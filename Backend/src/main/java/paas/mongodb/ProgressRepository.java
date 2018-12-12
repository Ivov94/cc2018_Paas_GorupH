package paas.mongodb;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProgressRepository extends MongoRepository<Progress, String> {
	
	Optional<Progress> findByImageName(String imageName);
}
