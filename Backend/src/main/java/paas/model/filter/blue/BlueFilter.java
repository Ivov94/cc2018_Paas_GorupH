package paas.model.filter.blue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import paas.model.filter.ImageFilter;

/**
 * This image filter is used to create a monochrome grey scale image of the colour blue.
 */
@Component
public class BlueFilter implements ImageFilter {

	public BlueFilter() {
	}
	
	@Override
	public byte[] createFilteredImage(final byte[] file) throws IOException {
		return createBlueImage(file);
	}
	
	private byte[] createBlueImage(byte[] bytes) throws IOException {
		BufferedImage image = createBufferedImage(bytes);
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int blueValue = new Color(image.getRGB(i, j)).getBlue();
				image.setRGB(
						i,
						j,
						new Color(
								blueValue,
								blueValue,
								blueValue
						).getRGB());
			}
		}
		
		return convertBufferedImageToByteArray(image);
	}
	
	private byte[] convertBufferedImageToByteArray(final BufferedImage image) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", bos);
		bos.flush();
		byte[] imageAsByteArray = bos.toByteArray();
		bos.close();
		
		return imageAsByteArray;
	}
	
	private BufferedImage createBufferedImage(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		BufferedImage bufferedImage = ImageIO.read(bis);
		bis.close();
		return bufferedImage;
	}
}
