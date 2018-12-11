package paas.model.task;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.awaitility.Duration;

import paas.model.api.DataStorageService;
import paas.model.api.JoinProcessor;
import paas.model.filter.ImageFilter;

/**
 * This is the main Task of performing the filtering of the images. When a Thread of this class gets executed,
 * all filters are executed in parallel and the process polls for the result periodically. Once all filters are done,
 * the image gets joined and stored.
 */
public class ImageProcessingTask implements Runnable {
	
	private static Duration MAX_POLL = Duration.FIVE_SECONDS;
	private static Duration POLL_INTERVAL = new Duration(50, TimeUnit.MILLISECONDS);
	
	private final List<ImageFilter> imageFilters;
	private final DataStorageService dataStorageService;
	private final JoinProcessor joinProcessor;
	private final byte[] imageFile;
	private final String imageName;
	
	private ImageProcessingTask(
			final List<ImageFilter> imageFilters,
			final DataStorageService dataStorageService,
			final JoinProcessor joinProcessor,
			final byte[] imageFile,
			final String imageName) {
		super();
		this.imageFilters = imageFilters;
		this.dataStorageService = dataStorageService;
		this.joinProcessor = joinProcessor;
		this.imageFile = imageFile;
		this.imageName = imageName;
	}
	
	public static ImageProcessingTask createImageProcessingTask(
			final List<ImageFilter> imageFilters,
			final DataStorageService dataStorageService,
			final JoinProcessor joinProcessor,
			final byte[] imageFile,
			final String imageName) {
		return new ImageProcessingTask(imageFilters, dataStorageService, joinProcessor, imageFile, imageName);
	}

	@Override
	public void run() {
		List<FilteringTask> myRunnableList = filterInParallel();
		try {
			performJoinImage(myRunnableList, imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<FilteringTask> filterInParallel() {
		List<FilteringTask> myRunnableList = imageFilters
				.stream()
				.map(filter -> FilteringTask.createImageFilterTask(filter, imageFile, dataStorageService, imageName))
				.collect(Collectors.toList());
		int numberofTries = 0;
		while (!isFinished(myRunnableList)) {
			numberofTries++;
			if (isIOException(myRunnableList) || (numberofTries == 3)) {
				System.out.println("System error");
				dataStorageService.updateProgressFail(imageName);
				break;
			}

			List<FilteringTask> activeRunnableList = myRunnableList
					.stream()
					.filter(task -> task.isActive())
					.collect(Collectors.toList());

			activeRunnableList
					.stream()
					.map(runnable -> new Thread(runnable))
					.forEach(Thread::start);

			await()
				.atMost(MAX_POLL)
				.pollInterval(POLL_INTERVAL)
				.conditionEvaluationListener(condition -> System.out.println(String.format("remaining time %dms", condition.getRemainingTimeInMS())))
				.until(() -> activeRunnableList.stream().allMatch(FilteringTask::isFinished), equalTo(true));
		}
		
		return myRunnableList;
	}
	
	private void performJoinImage(final List<FilteringTask> myRunnableList, String name) throws IOException {
		byte[] joinedImage = joinProcessor.joinImages(
				myRunnableList.get(0).getResultData(),
				myRunnableList.get(1).getResultData(),
				myRunnableList.get(2).getResultData(),
				myRunnableList.get(3).getResultData());
		dataStorageService.storeImage(joinedImage, name);
	}
	
	private boolean isFinished(List<FilteringTask> list) {
		for (FilteringTask run : list) {
			if (!run.isFinished()) {
				return false;
			}
		}
		return true;
	}

	private boolean isIOException(List<FilteringTask> list) {
		for (FilteringTask run : list) {
			if (run.isBroken()) {
				return true;
			}
		}
		return false;
	}

}
