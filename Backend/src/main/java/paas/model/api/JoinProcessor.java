package paas.model.api;

import java.io.IOException;

/**
 * This interface provides the functionality of joining different parts of the resultimage into a big picture.
 */
public interface JoinProcessor {

	/**
	 * Join the 4 images to make one big picture. The negative image is the top left part of the join and the red image is the top right part
	 * while bottom left and bottom right are the blue and the green image.
	 * @param negativeImage the negative image.
	 * @param redImage the red image.
	 * @param blueImage the blue image.
	 * @param greenImage the green image.
	 * @return the joined image as one picture.
	 * @throws IOException This exception is thrown when the image cannot be read.
	 */
	byte[] joinImages(byte[] negativeImage, byte[] redImage, byte[] blueImage, byte[] greenImage) throws IOException;
}
