package paas.model.api;

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
	 * Update the progress of a task in the database to increment the progress.
	 * @param progressKey the key to identify the task.
	 */
	void updateProgressIncrement(String progressKey);
	
	void updateProgressAllParallelTasks();
	
	/**
	 * Update a progress to indicate that it failed.
	 * @param progressKey the key to identify the task.
	 */
	void updateProgressFail(String progressKey);
}
