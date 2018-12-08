package paas.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DataHolder {
	
	private Map<String, byte[]> storedImages;
	
	public DataHolder() {
		storedImages = new HashMap<>();
	}

	public byte[] getImage(final String key) {
		return storedImages.get(key);
	}

	public void putImage(final String key, final byte[] data) {
		storedImages.put(key, data);
	}
}
