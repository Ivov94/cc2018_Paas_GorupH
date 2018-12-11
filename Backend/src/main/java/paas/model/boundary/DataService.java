package paas.model.boundary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import paas.model.api.DataRetrieverService;
import paas.model.api.DataStorageService;
import paas.mongodb.Image;
import paas.mongodb.ImageRepository;
import paas.mongodb.Progress;

@Component
public class DataService implements DataStorageService, DataRetrieverService {

	//TODO DataHolder is a temporal class that should be get rid of. Replace it with MongoRepository
	//see: https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html -> 6.3.1 Geo-spatial repository queries
	
	@Autowired
	private ImageRepository imageRepository;
	
	public DataService() {
	}
	
	@Override
	public Image retrieveImage(String key) throws FileNotFoundException {
		return imageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
	}
	
	@Override
	public Progress retrieveProgress(String key) throws FileNotFoundException {
		// TODO Create corresponding repository and retrieve the progress here
		return null;
	}

	@Override
	public void storeImage(byte[] imageToStore, String key) {
		imageRepository.save(new Image(key, key, imageToStore, key, key, new Date(), 5, true));
	    try {
	    	File convFile = new File(key + ".png");
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(imageToStore);
		    fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProgressIncrement(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateProgressAllParallelTasks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProgressFail(String progressKey) {
		// TODO Auto-generated method stub
		
	}
}
