package paas.rest.model.filter.red;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import paas.rest.model.filter.ImageFilter;

/**
 * This image filter is used to create a monochrome grey scale image of the colour red.
 */
@Component
public class RedFilter implements ImageFilter {

	public RedFilter() {
	}
	
	@Override
	public void createAndStoreFilteredImage(final MultipartFile file) throws IOException {
		File convFile = new File("red_" + file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile);
	    
	    fos.write(createRedImage(file.getBytes()));
	    fos.close();
	}
	
	private byte[] createRedImage(byte[] bytes) throws IOException {
		BufferedImage image = createBufferedImage(bytes);
		BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int redValue = new Color(image.getRGB(i, j)).getRed();
				filteredImage.setRGB(
						i,
						j,
						new Color(
								redValue,
								redValue,
								redValue
						).getRGB());
			}
		}
		
		return convertBufferedImageToByteArray(filteredImage);
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
