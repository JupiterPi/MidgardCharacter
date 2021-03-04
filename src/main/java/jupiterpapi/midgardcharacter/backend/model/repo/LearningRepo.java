package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LearningRepo extends MongoRepository<LearningDB, String> {
    List<LearningDB> findByCharacterId(String characterId);
}
