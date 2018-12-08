package paas.rest;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

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
import paas.model.task.Task;

@RestController
public class FileUploadController {
	private static Duration MAX_POLL = Duration.FIVE_SECONDS;
	private static Duration POLL_INTERVAL = new Duration(50, TimeUnit.MILLISECONDS);

	@Autowired
	private List<ImageFilter> imageFilters;
	
	@Autowired
	private DataStorageService dataStorageService;
	
	@Autowired
	private JoinProcessor joinProcessor;
	
	public FileUploadController() {
	}
	
	@PostMapping("/img")
    public String handleFileUploadMultiThread(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		List<Task> tasks = imageFilters.stream().map(filter -> Task.createImageFilterTask(filter, file)).collect(Collectors.toList());
		tasks.forEach(Task::start);
		
		await().atMost(MAX_POLL).pollInterval(POLL_INTERVAL).until(() -> tasks.stream().allMatch(Task::isFinished), equalTo(true));
		
		byte[] joinedImage;
		try {
			joinedImage = joinProcessor.joinImages(
					tasks.get(0).getResultData(),
					tasks.get(1).getResultData(),
					tasks.get(2).getResultData(),
					tasks.get(3).getResultData());
			dataStorageService.storeImage(joinedImage, "joinedImage.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "redirect:/";
    }
}
