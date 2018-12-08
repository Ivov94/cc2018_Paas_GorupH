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
}
