package jupiterpapi.midgardcharacter.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserDB,String> {
}
