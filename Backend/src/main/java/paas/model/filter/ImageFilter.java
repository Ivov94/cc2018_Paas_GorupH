package paas.model.filter;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * This interface provides functionality of creating a filtered image and storing it.
 *
 */
public interface ImageFilter {
	
	//TODO for all filters: make parallelization by subdividing the image -> performance + fault tolerance + intraprocess parallelization
	/**
	 * Create a filtered version of the image.
	 *
	 * @param file the image to be filtered as a file.
	 * @return the filtered image as a byte array.
	 * @throws IOException This exception is thrown when the image can not be written.
	 */
	byte[] createFilteredImage(MultipartFile file) throws IOException;
}
