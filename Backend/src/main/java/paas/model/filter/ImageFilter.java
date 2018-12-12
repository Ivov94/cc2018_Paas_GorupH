package paas.model.filter;

import java.io.IOException;

/**
 * This interface provides functionality of creating a filtered image and storing it.
 *
 */
public interface ImageFilter {
	
	/**
	 * Create a filtered version of the image.
	 *
	 * @param file the image to be filtered as a byte array.
	 * @return the filtered image as a byte array.
	 * @throws IOException This exception is thrown when the image can not be written.
	 */
	byte[] createFilteredImage(byte[] file) throws IOException;
}
