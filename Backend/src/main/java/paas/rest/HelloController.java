package paas.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import paas.rest.model.DataHolder;

@RestController
public class HelloController {
	@Autowired
	private DataHolder dataHolder;
	
	public HelloController() {
	}

	@RequestMapping("/hello")
    public String index() {
		String prefix = "No File uploaded yet.";
		
		if (dataHolder.getFile() != null) {
			prefix = dataHolder.getFile().getOriginalFilename() + " " +dataHolder.getFile().getContentType();
		}
		
        return prefix + "\n" + Arrays.toString(dataHolder.getData());
    }
}
