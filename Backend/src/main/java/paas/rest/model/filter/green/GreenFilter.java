package paas.rest.model.filter.green;

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
 * This image filter is used to create a monochrome grey scale image of the colour green.
 */
@Component
public class GreenFilter implements ImageFilter {

	public GreenFilter() {
	}
	
	@Override
	public void createAndStoreFilteredImage(final MultipartFile file) throws IOException {
		File convFile = new File("green_" + file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile);
	    
	    fos.write(createGreenImage(file.getBytes()));
	    fos.close();
	}
	
	private byte[] createGreenImage(byte[] bytes) throws IOException {
		BufferedImage image = createBufferedImage(bytes);
		BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				int greenValue = new Color(image.getRGB(i, j)).getGreen();
				filteredImage.setRGB(
						i,
						j,
						new Color(
								greenValue,
								greenValue,
								greenValue
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
