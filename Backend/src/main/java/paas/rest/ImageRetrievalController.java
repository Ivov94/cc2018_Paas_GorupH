package paas.rest;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import paas.model.api.DataRetrieverService;
import paas.mongodb.Image;

@RestController
public class ImageRetrievalController {

	@Autowired
	private DataRetrieverService dataRetrieverService;
	
	public ImageRetrievalController() {
	}

	@RequestMapping(value = "/getImage/{imageName}", method = { RequestMethod.GET })
    public ResponseEntity<Image> getImage(@PathVariable String imageName) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	    ResponseEntity<Image> response = new ResponseEntity<Image>(headers, HttpStatus.NOT_FOUND);
    	try {
			response = new ResponseEntity<Image>(dataRetrieverService.retrieveImage(imageName), headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
	    return response;
    }
}
