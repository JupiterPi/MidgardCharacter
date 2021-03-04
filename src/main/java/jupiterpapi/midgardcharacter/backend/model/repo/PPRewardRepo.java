package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PPRewardRepo extends MongoRepository<PPRewardDB, String> {
    List<PPRewardDB> findByCharacterId(String characterId);
}
