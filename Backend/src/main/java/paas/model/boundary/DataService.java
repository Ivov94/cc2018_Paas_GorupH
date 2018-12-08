package paas.model.boundary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import paas.model.DataHolder;
import paas.model.api.DataRetrieverService;
import paas.model.api.DataStorageService;

@Component
public class DataService implements DataStorageService, DataRetrieverService {

	//TODO DataHolder is a temporal class that should be get rid of. Replace it with MongoRepository
	//see: https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html -> 6.3.1 Geo-spatial repository queries
	@Autowired
	private DataHolder dataHolder;
	
	public DataService() {
	}
	
	@Override
	public byte[] retrieveImage(String key) {
		return dataHolder.getImage(key);
	}

	@Override
	public void storeImage(byte[] imageToStore, String key) {
		dataHolder.putImage(key, imageToStore);
		File convFile = new File(key);
	    try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(imageToStore);
		    fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
