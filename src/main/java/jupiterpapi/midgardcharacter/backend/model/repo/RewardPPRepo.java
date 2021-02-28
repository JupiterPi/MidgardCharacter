package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RewardPPRepo extends MongoRepository<RewardPPDB,String> {
    List<RewardPPDB> findByCharacterId(String characterId);
}
