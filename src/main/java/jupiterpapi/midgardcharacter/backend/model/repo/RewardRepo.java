package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RewardRepo extends MongoRepository<RewardDB,String> {
    List<RewardDB> findByCharacterId(String characterId);
}
