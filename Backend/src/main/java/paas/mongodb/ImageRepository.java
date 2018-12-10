package paas.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface ImageRepository extends MongoRepository<Image, String> {
	
	public Image findByName(String name);
	
	public List<Image> findListByName(String name);
	
	//public Image addImage(Image image);
	
	//public Image updateImage(Image image);
	
}
