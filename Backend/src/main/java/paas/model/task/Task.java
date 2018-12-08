package paas.model.task;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import paas.model.filter.ImageFilter;

public class Task extends Thread {
	private ImageFilter filter;
	private MultipartFile file;
	
	private boolean taskFinished;
	private byte[] resultData;
	
	private Task(final ImageFilter filter, final MultipartFile file) {
		this.filter = filter;
		this.file = file;
		this.taskFinished = false;
	}
	
	public static Task createImageFilterTask(final ImageFilter filter, final MultipartFile file) {
		return new Task(filter, file);
	}
	
	public void run() {
		try {
			resultData = filter.createFilteredImage(file);
			taskFinished = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isFinished() {
		return taskFinished;
	}
	
	public byte[] getResultData() {
		return resultData;
	}
}
