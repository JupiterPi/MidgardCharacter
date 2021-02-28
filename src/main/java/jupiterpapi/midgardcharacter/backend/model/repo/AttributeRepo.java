package jupiterpapi.midgardcharacter.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttributeRepo extends MongoRepository<AttributeDB,String> {
    List<AttributeDB> findByCharacterId(String characterId);
}
