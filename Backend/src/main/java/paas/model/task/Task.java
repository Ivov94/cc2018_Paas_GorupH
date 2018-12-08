package paas.model.task;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import paas.model.filter.ImageFilter;

/**
 * A filtering task can be started by this class and when it is done, the result can be fetched.
 */
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
	
	/**
	 * Create an executable Task using the given image filter.
	 * @param filter the filter to be used.
	 * @param file the file holding the image that is filtered.
	 * @return An instance of this class, ready to be executed.
	 */
	public static Task createImageFilterTask(final ImageFilter filter, final MultipartFile file) {
		return new Task(filter, file);
	}
	
	public void run() {
		try {
			//TODO publish an indication of the progress -> progressbar.
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
