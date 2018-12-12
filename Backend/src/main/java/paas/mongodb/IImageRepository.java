package paas.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import paas.mongodb.Image.UpdateProgress;

@Service
public interface IImageRepository extends MongoRepository<Image, String> {
	
	public Optional<Image> findByName(String name);
	
	public List<Image> findListByName(String name);
	
	//public void UpdateProg1(String name, Image.UpdateProgress progress1);
	
	//public void UpdateProg2(String name, Image.UpdateProgress progress2);
	
	//public void UpdateProg3(String name, Image.UpdateProgress progress3);
	
	//public void UpdateProg4(String name, Image.UpdateProgress progress4);
	
}
