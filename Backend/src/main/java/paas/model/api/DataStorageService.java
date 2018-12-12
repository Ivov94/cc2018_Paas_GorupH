package paas.model.api;

import paas.model.filter.ImageFilter;

/**
 * This interface offers the functionality to store Images in the Database.
 */
public interface DataStorageService {

	/**
	 * Store the image into the database.
	 * @param imageToStore The image that shall be stored is a byte array.
	 * @param key the identifier by which the image is retrievable
	 */
	void storeImage(byte[] imageToStore, String key);
	
	/**
	 * Store an Object of the class Progress in the database to track the progress of a task.
	 * @param progressKey the key to retrieve the progress.
	 */
	void createProgressTracking(String progressKey);
	
	/**
	 * Update the progress of a task in the database to indicate that a task is done progress.
	 * @param progressKey the key to identify the task.
	 */
	void updateProgressFilter(String progressKey, ImageFilter filter);
	
	/**
	 * Update the progress of a task in the database to indicate that the image join is done.
	 * @param progressKey the key to identify the task.
	 */
	void updateProgressJoin(String progressKey);
	
	/**
	 * Update the progress of a task to indicate that all filters are done.
	 * @param progressKey
	 */
	void updateProgressAllParallelTasks(String progressKey);
	
	/**
	 * Update a progress to indicate that it failed.
	 * @param progressKey the key to identify the task.
	 * @param message the message to be added to the failed progress.
	 */
	void updateProgressFail(String progressKey, String message);
}
