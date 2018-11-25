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
        return "Greetings from Spring Boot! " + dataHolder.getFile().getContentType() + "\n" + Arrays.toString(dataHolder.getData());
    }
}
