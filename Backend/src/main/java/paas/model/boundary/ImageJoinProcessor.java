package paas.model.boundary;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import paas.model.api.JoinProcessor;

@Component
public class ImageJoinProcessor implements JoinProcessor {

	public ImageJoinProcessor() {
	}
	
	@Override
	public byte[] joinImages(byte[] negativeImage, byte[] redImage, byte[] blueImage, byte[] greenImage) throws IOException {
		return join(negativeImage, redImage, blueImage, greenImage);
	}
	
	private byte[] join(byte[] negativeImage, byte[] redImage, byte[] blueImage, byte[] greenImage) throws IOException {
		
		BufferedImage negImage = createBufferedImage(negativeImage);
		BufferedImage redImg = createBufferedImage(redImage);
		BufferedImage greenImg = createBufferedImage(greenImage);
		BufferedImage blueImg = createBufferedImage(blueImage);
		BufferedImage image = new BufferedImage(negImage.getWidth()*2, negImage.getHeight()*2, negImage.getType());
		
		for(int i = 0; i < blueImg.getWidth(); i++) {
			for(int j = 0; j < blueImg.getHeight(); j++) {
				image.setRGB(i, j, blueImg.getRGB(i, j));
			}
		}
		
		for(int i = 0; i < greenImg.getWidth(); i++) {
			for(int j = 0; j < greenImg.getHeight(); j++) {
				image.setRGB(i + greenImg.getWidth(), j, greenImg.getRGB(i, j));
			}
		}
		
		for(int i = 0; i < redImg.getWidth(); i++) {
			for(int j = 0; j < redImg.getHeight(); j++) {
				image.setRGB(i, j + redImg.getHeight(), redImg.getRGB(i, j));
			}
		}
		
		for(int i = 0; i < negImage.getWidth(); i++) {
			for(int j = 0; j < negImage.getHeight(); j++) {
				image.setRGB(i + negImage.getWidth(), j + negImage.getHeight(), negImage.getRGB(i, j));
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
