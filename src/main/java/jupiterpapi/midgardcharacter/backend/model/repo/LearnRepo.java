package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LearnRepo extends MongoRepository<LearnDB,String> {
    List<LearnDB> findByCharacterId(String characterId);
}
