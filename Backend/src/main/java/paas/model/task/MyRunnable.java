package paas.model.task;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.multipart.MultipartFile;

import paas.model.filter.ImageFilter;

public class MyRunnable implements Runnable {

	private ImageFilter filter;
	private MultipartFile file;
	
	private States state;
	private byte[] resultData;
	
	private MyRunnable(final ImageFilter filter, final MultipartFile file) {
		this.filter = filter;
		this.file = file;
		this.state = States.READY;
	}
	
	public static MyRunnable createImageFilterTask(final ImageFilter filter, final MultipartFile file) {
		return new MyRunnable(filter, file);
	}
	
	public enum States {
		READY,
		DONE,
		BROKEN
	}
	
	public void run() {
		try {
			//TODO publish an indication of the progress -> progressbar.
			resultData = filter.createFilteredImage(file);
			state = States.DONE;
		} catch (IOException e) {
			state = States.BROKEN;
			e.printStackTrace();
		}
	}
	
	public boolean isFinished() {
		return state == States.DONE;
	}
	
	public boolean isActive() {
		return state == States.READY;
	}
	
	public boolean isBroken() {
		return state == States.BROKEN;
	}
	
	public byte[] getResultData() {
		return resultData;
	}

}
