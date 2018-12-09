package paas.model.task;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.multipart.MultipartFile;

import paas.model.filter.ImageFilter;

public class MyRunnable implements Runnable {

	private ImageFilter filter;
	private MultipartFile file;
	
	//Atomic integer values : 1 -> not finished, 2 -> finished, 3 -> interrupted
	private AtomicInteger taskFinished;
	private byte[] resultData;
	
	private MyRunnable(final ImageFilter filter, final MultipartFile file) {
		this.filter = filter;
		this.file = file;
		this.taskFinished = new AtomicInteger(1);
	}
	
	public static MyRunnable createImageFilterTask(final ImageFilter filter, final MultipartFile file) {
		return new MyRunnable(filter, file);
	}
	
	public void run() {
		try {
			//TODO publish an indication of the progress -> progressbar.
			resultData = filter.createFilteredImage(file);
			taskFinished = new AtomicInteger(2);
		} catch (IOException e) {
			taskFinished = new AtomicInteger(3);
			e.printStackTrace();
		}
	}
	
	public int isFinished() {
		return taskFinished.get();
	}
	
	public boolean isActive() {
		return taskFinished.get() == 1;
	}
	
	public boolean isCompleted() {
		return taskFinished.get() == 2;
	}
	
	public byte[] getResultData() {
		return resultData;
	}

}
