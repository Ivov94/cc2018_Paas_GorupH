package paas.model.boundary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import paas.model.api.DataRetrieverService;
import paas.model.api.DataStorageService;

@Component
public class DataService implements DataStorageService, DataRetrieverService {

	public DataService() {
	}
	
	@Override
	public byte[] storeImage(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeImage(byte[] imageToStore, String key) {
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
