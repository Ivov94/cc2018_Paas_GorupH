package paas.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface ImageRepository extends MongoRepository<Image, String> {
	
	public Optional<Image> findByName(String name);
	
	public List<Image> findListByName(String name);
}
