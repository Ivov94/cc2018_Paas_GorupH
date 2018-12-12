package paas.mongodb;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

public class Progress {

	//TODO make this class as an indicator for the state of the process.
	@Autowired
	private IImageRepository ImageRepository;
	
	public Progress() {
	}
	
	public void UpdateProgress1(String key, Image.UpdateProgress process1) throws FileNotFoundException
	{
		Image image = ImageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
		image.progress1 = process1;
		ImageRepository.save(image);
		
		this.CalculateCount(image);
	}
	
	public void UpdateProgress2(String key, Image.UpdateProgress process2) throws FileNotFoundException
	{
		Image image = ImageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
		image.progress2 = process2;
		ImageRepository.save(image);
		this.CalculateCount(image);
	}
	
	public void UpdateProgress3(String key, Image.UpdateProgress process3) throws FileNotFoundException
	{
		Image image = ImageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
		image.progress3 = process3;
		ImageRepository.save(image);
		this.CalculateCount(image);
	}
	
	public void UpdateProgress4(String key, Image.UpdateProgress process4) throws FileNotFoundException
	{
		Image image = ImageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
		image.progress4 = process4;
		ImageRepository.save(image);
		this.CalculateCount(image);
	}
	
	private int CalculateCount(Image image)
	{
		int count = 0;
		
		if(image.progress1 == Image.UpdateProgress.Done)
			count++;
		if(image.progress2 == Image.UpdateProgress.Done)
			count++;
		if(image.progress3 == Image.UpdateProgress.Done)
			count++;
		if(image.progress4 == Image.UpdateProgress.Done)
			count++;
		
		return count;
	}
}
