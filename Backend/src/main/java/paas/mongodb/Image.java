package paas.mongodb;

import java.util.Date;
import org.springframework.data.annotation.Id;

public class Image {
	
	@Id
	public String id;
	
	public String name;
	public int data;
	public String mimeType;
	public String filename;
	public Date date;
	public int step;
	public boolean isDone;
	
	public Image()
	{
	}

	public Image(String id, String name, int data, String mimeType, String filename, Date date, int step,
			boolean isDone) {
		super();
		this.id = id;
		this.name = name;
		this.data = data;
		this.mimeType = mimeType;
		this.filename = filename;
		this.date = date;
		this.step = step;
		this.isDone = isDone;
	}
	
	
	
}
