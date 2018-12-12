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
import paas.model.filter.ImageFilter;
import paas.model.filter.blue.BlueFilter;
import paas.model.filter.green.GreenFilter;
import paas.model.filter.negative.NegativeImageFilter;
import paas.model.filter.red.RedFilter;
import paas.mongodb.Image;
import paas.mongodb.ImageRepository;
import paas.mongodb.Progress;
import paas.mongodb.ProgressRepository;

@Component
public class DataService implements DataStorageService, DataRetrieverService {
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired ProgressRepository progressRepository;
	
	public DataService() {
	}
	
	@Override
	public Image retrieveImage(final String key) throws FileNotFoundException {
		return imageRepository.findByName(key).orElseThrow(() -> new FileNotFoundException(key));
	}
	
	@Override
	public Progress retrieveProgress(final String key) throws FileNotFoundException {
		return progressRepository.findByImageName(key).orElseThrow(() -> new FileNotFoundException(key));
	}

	@Override
	public void storeImage(final byte[] imageToStore, final String key) {
		imageRepository.save(new Image(key, key, imageToStore, key, key, new Date()));
	}
	
	@Override
	public void updateProgressFilter(final String progressKey, final ImageFilter filter) {
		try {
			Progress progress = retrieveProgress(progressKey);
			
			if (filter instanceof NegativeImageFilter) {
				progress.setFilterNegativeDone(true);
			} else if (filter instanceof RedFilter) {
				progress.setFilterRedDone(true);
			} else if (filter instanceof GreenFilter) {
				progress.setFilterGreenDone(true);
			} else if (filter instanceof BlueFilter) {
				progress.setFilterBlueDone(true);
			}
			progressRepository.save(progress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateProgressJoin(final String progressKey) {
		try {
			Progress progress = retrieveProgress(progressKey);
			progress.setFilterNegativeDone(true);
			progress.setFilterRedDone(true);
			progress.setFilterGreenDone(true);
			progress.setFilterBlueDone(true);
			progress.setImageJoinDone(true);
			progressRepository.save(progress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateProgressAllParallelTasks(final String progressKey) {
		try {
			Progress progress = retrieveProgress(progressKey);
			progress.setFilterNegativeDone(true);
			progress.setFilterRedDone(true);
			progress.setFilterGreenDone(true);
			progress.setFilterBlueDone(true);
			progressRepository.save(progress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProgressFail(final String progressKey, final String message) {
		try {
			Progress progress = retrieveProgress(progressKey);
			progress.setFailed(true);
			progress.setMessage(message);
			progressRepository.save(progress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void createProgressTracking(String progressKey) {
		progressRepository.save(new Progress(progressKey));
	}
}
