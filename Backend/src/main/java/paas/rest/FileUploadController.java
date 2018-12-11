package paas.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import paas.model.api.DataStorageService;
import paas.model.api.JoinProcessor;
import paas.model.filter.ImageFilter;
import paas.model.task.ImageProcessingTask;

@RestController
public class FileUploadController {

	@Autowired
	private List<ImageFilter> imageFilters;

	@Autowired
	private DataStorageService dataStorageService;

	@Autowired
	private JoinProcessor joinProcessor;

	public FileUploadController() {
	}

	@RequestMapping(value = "/img", method = { RequestMethod.POST })
	public ResponseEntity<String> handleFileUploadMultiThread(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name) {
		ResponseEntity<String> response = new ResponseEntity<>(name, HttpStatus.OK);
		ImageProcessingTask imageProcessingTask;
		try {
			imageProcessingTask = ImageProcessingTask
					.createImageProcessingTask(imageFilters, dataStorageService, joinProcessor, file.getBytes(), name);
			new Thread(imageProcessingTask).start();
		} catch (IOException e) {
			response = new ResponseEntity<>("Could not read file", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		return response;
	}
}
