package paas.rest;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import org.awaitility.Awaitility.*;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.awaitility.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import paas.model.api.DataStorageService;
import paas.model.api.JoinProcessor;
import paas.model.filter.ImageFilter;
import paas.model.task.MyRunnable;


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
	
	private boolean isFinished(List<MyRunnable> list) {
		for (MyRunnable run : list) {
			if (!run.isFinished()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isIOException(List<MyRunnable> list) {
		for (MyRunnable run : list) {
			if (run.isBroken()) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/img", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	//@PostMapping("/img")
    public String handleFileUploadMultiThread(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		List<MyRunnable> myRunnableList = imageFilters.stream()
				.map(filter -> MyRunnable.createImageFilterTask(filter, file)).collect(Collectors.toList());
		int numberofTries = 0;
		while (isFinished(myRunnableList)) {
			numberofTries++;
			if (isIOException(myRunnableList) || (numberofTries == 3)) {
				System.out.println("System error");
				return "redirect:/";
			}
			
			List<MyRunnable> activeRunnableList = myRunnableList.stream().filter(predicate -> predicate.isActive()).map(runnable -> runnable).collect(Collectors.toList());
			
			List<Thread> tasks = activeRunnableList.stream().map(runnable -> new Thread(runnable)).collect(Collectors.toList());
			tasks.forEach(Thread::start);
			
			await().atMost(Duration.FIVE_SECONDS).pollInterval(POLL_INTERVAL)
			.conditionEvaluationListener(condition -> System.out.println(String.format("remaining time %dms \n", condition.getRemainingTimeInMS())))
								.until(() -> activeRunnableList.stream().allMatch(MyRunnable::isFinished), equalTo(true));
			
			
		}
		
		byte[] joinedImage;
		try {
			joinedImage = joinProcessor.joinImages(
					myRunnableList.get(0).getResultData(),
					myRunnableList.get(1).getResultData(),
					myRunnableList.get(2).getResultData(),
					myRunnableList.get(3).getResultData());
			dataStorageService.storeImage(joinedImage, "joinedImage.png");
			writeImage(joinedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "redirect:/";
    }
	
	private void writeImage(byte[] aByteArray ) {
		int width = 1;
		int height = 2;

		DataBuffer buffer = new DataBufferByte(aByteArray, aByteArray.length);

		//3 bytes per pixel: red, green, blue
		WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 3 * width, 3, new int[] {0, 1, 2}, (Point)null);
		ColorModel cm = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE); 
		BufferedImage image = new BufferedImage(cm, raster, true, null);

		try {
			ImageIO.write(image, "png", new File("image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
