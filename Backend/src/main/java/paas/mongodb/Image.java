package paas.mongodb;

import java.util.Date;
import org.springframework.data.annotation.Id;



public class Image {
	
	@Id
	public String id;
	
	public String name;
	public byte[] data;
	public String mimeType;
	public String filename;
	public Date date;
	public UpdateProgress progress1;
	public UpdateProgress progress2;
	public UpdateProgress progress3;
	public UpdateProgress progress4;
	public boolean isDone;
	
	public enum UpdateProgress
	{
		NotStarted,
		InProcess,
		Done
	}
	
	public Image()
	{
	}

	public Image(String id, String name, byte[] data, String mimeType, String filename, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.data = data;
		this.mimeType = mimeType;
		this.filename = filename;
		this.date = date;
		this.progress1 = UpdateProgress.NotStarted;
		this.progress2 = UpdateProgress.NotStarted;
		this.progress3 = UpdateProgress.NotStarted;
		this.progress4 = UpdateProgress.NotStarted;
		this.isDone = false;
				
	}
	
}
