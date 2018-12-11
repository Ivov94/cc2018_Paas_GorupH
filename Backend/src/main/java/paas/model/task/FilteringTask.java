package paas.model.task;

import java.io.IOException;

import paas.model.api.DataStorageService;
import paas.model.filter.ImageFilter;

public class FilteringTask implements Runnable {
	private final DataStorageService dataStorageService;
	private final String processKey;
	private final ImageFilter filter;
	private final byte[] file;
	
	private States state;
	private byte[] resultData;
	
	private FilteringTask(
			final ImageFilter filter,
			final byte[] file,
			final DataStorageService dataStorageService,
			final String processKey) {
		this.filter = filter;
		this.processKey = processKey;
		this.file = file;
		this.dataStorageService = dataStorageService;
		this.state = States.READY;
	}
	
	public static FilteringTask createImageFilterTask(
			final ImageFilter filter,
			final byte[] file,
			final DataStorageService dataStorageService,
			final String processKey) {
		return new FilteringTask(filter, file, dataStorageService, processKey);
	}
	
	public enum States {
		READY,
		DONE,
		BROKEN
	}
	
	public void run() {
		try {
			resultData = filter.createFilteredImage(file);
			state = States.DONE;
			dataStorageService.updateProgressIncrement(processKey);
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
