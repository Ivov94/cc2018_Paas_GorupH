package paas.rest;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import org.awaitility.Awaitility.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.awaitility.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import paas.model.api.DataStorageService;
import paas.model.api.JoinProcessor;
import paas.model.filter.ImageFilter;
import paas.model.task.MyRunnable;
import paas.model.task.Task;

@RestController
public class FileUploadControllerNew {
	private static Duration MAX_POLL = Duration.FIVE_SECONDS;
	private static Duration POLL_INTERVAL = new Duration(50, TimeUnit.MILLISECONDS);

	@Autowired
	private List<ImageFilter> imageFilters;
	
	@Autowired
	private DataStorageService dataStorageService;
	
	@Autowired
	private JoinProcessor joinProcessor;
	
	public FileUploadControllerNew() {
	}
	
	private boolean isFinished(List<MyRunnable> list) {
		for (MyRunnable run : list) {
			if (run.isFinished() == 1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isIOException(List<MyRunnable> list) {
		for (MyRunnable run : list) {
			if (run.isFinished() == 3) {
				return true;
			}
		}
		return false;
	}
	
	@PostMapping("/img")
    public String handleFileUploadMultiThread(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		List<MyRunnable> myRunnableList = imageFilters.stream()
				.map(filter -> MyRunnable.createImageFilterTask(filter, file)).collect(Collectors.toList());
		int numberofTries = 0;
		while (isFinished(myRunnableList)) {
			numberofTries++;
			if (isIOException(myRunnableList) || (numberofTries == 3)) {
				System.out.println("Ne valja slika");
				return "redirect:/";
			}
			
			List<MyRunnable> activeRunnableList = myRunnableList.stream().filter(predicate -> predicate.isActive()).map(runnable -> runnable).collect(Collectors.toList());
			
			List<Thread> tasks = activeRunnableList.stream().map(runnable -> new Thread(runnable)).collect(Collectors.toList());
			tasks.forEach(Thread::start);
			
			await().atMost(Duration.TEN_SECONDS).pollInterval(POLL_INTERVAL)
			.conditionEvaluationListener(condition -> System.out.println("Jedna slika dobro prosla"))
								.until(() -> activeRunnableList.stream().allMatch(MyRunnable::isCompleted), equalTo(true));
			
			
		}
		
		byte[] joinedImage;
		try {
			joinedImage = joinProcessor.joinImages(
					myRunnableList.get(0).getResultData(),
					myRunnableList.get(1).getResultData(),
					myRunnableList.get(2).getResultData(),
					myRunnableList.get(3).getResultData());
			dataStorageService.storeImage(joinedImage, "joinedImage.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "redirect:/";
    }
}
