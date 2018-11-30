package paas.rest.model.filter;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * This interface provides functionality of creating a filtered image and storing it.
 *
 */
public interface ImageFilter {
	
	/**
	 * Create and store a filtered version of the image.
	 *
	 * @param file the image to be filtered as a file.
	 * @throws IOException This exception is thrown when the image can not be written.
	 */
	void createAndStoreFilteredImage(MultipartFile file) throws IOException;

}
