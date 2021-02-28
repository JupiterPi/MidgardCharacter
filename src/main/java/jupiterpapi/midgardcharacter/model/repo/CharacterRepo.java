package jupiterpapi.midgardcharacter.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepo extends MongoRepository<CharacterDB,String> {
    List<CharacterDB> findByUserId(String userId);
}