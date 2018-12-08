package paas.model.filter.negative;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import paas.model.filter.ImageFilter;

/**
 * This image filter is used to create a negative image.
 */
@Component
public class NegativeImageFilter implements ImageFilter {

	public NegativeImageFilter() {
	}
	
	@Override
	public byte[] createFilteredImage(final MultipartFile file) throws IOException {
		return createNegativeImage(file.getBytes());
	}
	
	private byte[] createNegativeImage(byte[] bytes) throws IOException {
		BufferedImage image = createBufferedImage(bytes);

		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				Color pixelColour = new Color(image.getRGB(i, j));
				image.setRGB(
						i,
						j,
						new Color(
								255 - pixelColour.getRed(),
								255 - pixelColour.getGreen(),
								255 - pixelColour.getBlue()
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

	@Override
	public String getKey() {
		return "negative_";
	}

}
