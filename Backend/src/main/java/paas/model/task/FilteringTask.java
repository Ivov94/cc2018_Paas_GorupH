package paas.model.task;

import java.io.IOException;

import paas.model.filter.ImageFilter;

public class FilteringTask implements Runnable {

	private ImageFilter filter;
	private byte[] file;
	
	private States state;
	private byte[] resultData;
	
	private FilteringTask(final ImageFilter filter, final byte[] file) {
		this.filter = filter;
		this.file = file;
		this.state = States.READY;
	}
	
	public static FilteringTask createImageFilterTask(final ImageFilter filter, final byte[] file) {
		return new FilteringTask(filter, file);
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
