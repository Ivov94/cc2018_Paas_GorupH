package paas.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import paas.rest.model.DataHolder;

@RestController
public class FileUploadController {
	
	@Autowired
	private DataHolder dataHolder;
	
	public FileUploadController() {
	}

	@PostMapping("/img")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		try {
			dataHolder.setData(file.getBytes());
			dataHolder.setFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("hi");
        return "redirect:/";
    }
}
