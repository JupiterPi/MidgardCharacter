package jupiterpapi.midgardcharacter.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LevelUpRepo extends MongoRepository<LevelUpDB,String> {
    List<LevelUpDB> findByCharacterId(String characterId);
}
