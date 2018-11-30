package paas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import paas.rest.model.DataHolder;

@RestController
public class ImageRetrievalController {

	@Autowired
	private DataHolder dataHolder;
	
	public ImageRetrievalController() {
	}

	@GetMapping("/getImage/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	    return new ResponseEntity<>(dataHolder.getData(), headers, HttpStatus.OK);
    }
}
