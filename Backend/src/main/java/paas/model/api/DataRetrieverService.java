package paas.model.api;

import java.io.FileNotFoundException;

import paas.mongodb.Image;
import paas.mongodb.Progress;

/**
 * This interface offers the functionality to retrieve Images from the Database.
 */
public interface DataRetrieverService {

	/**
	 * Get an image from the database by a key.
	 * @param key the identifier by which the image is retrievable.
	 * @return the image found by the key.
	 * @throws FileNotFoundException is thrown if the image cannot be found.
	 */
	Image retrieveImage(String key) throws FileNotFoundException;
	
	/**
	 * Get the progress on a running task identified by a key.
	 * @param key the identifier by which the task is retrievable.
	 * @return the current progress of the task.
	 * @throws FileNotFoundException is thrown if the task cannot be found.
	 */
	Progress retrieveProgress(String key) throws FileNotFoundException;
}
