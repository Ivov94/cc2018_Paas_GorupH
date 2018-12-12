package paas.mongodb;

import org.springframework.data.annotation.Id;

public class Progress {
	
	@Id
	private String imageName;
	
	private boolean failed;
	private String message;
	
	private boolean filterNegativeDone;
	private boolean filterRedDone;
	private boolean filterGreenDone;
	private boolean filterBlueDone;
	
	private boolean imageJoinDone;
	
	public Progress(final String imageName) {
		this.imageName = imageName;
		this.failed = false;
		this.message = "";
		this.filterNegativeDone = false;
		this.filterRedDone = false;
		this.filterGreenDone = false;
		this.filterBlueDone = false;
		this.imageJoinDone = false;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFilterNegativeDone() {
		return filterNegativeDone;
	}

	public void setFilterNegativeDone(boolean filterNegativeDone) {
		this.filterNegativeDone = filterNegativeDone;
	}

	public boolean isFilterRedDone() {
		return filterRedDone;
	}

	public void setFilterRedDone(boolean filterRedDone) {
		this.filterRedDone = filterRedDone;
	}

	public boolean isFilterGreenDone() {
		return filterGreenDone;
	}

	public void setFilterGreenDone(boolean filterGreenDone) {
		this.filterGreenDone = filterGreenDone;
	}

	public boolean isFilterBlueDone() {
		return filterBlueDone;
	}

	public void setFilterBlueDone(boolean filterBlueDone) {
		this.filterBlueDone = filterBlueDone;
	}

	public boolean isImageJoinDone() {
		return imageJoinDone;
	}

	public void setImageJoinDone(boolean imageJoinDone) {
		this.imageJoinDone = imageJoinDone;
	}
}
