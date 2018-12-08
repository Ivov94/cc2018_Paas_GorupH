package paas.model.api;

/**
 * This interface offers the functionality to retrieve Images from the Database.
 */
public interface DataRetrieverService {

	/**
	 * Get an image from the database by a key.
	 * @param key the identifier by which the image is retrievable.
	 */
	byte[] retrieveImage(String key);
}
