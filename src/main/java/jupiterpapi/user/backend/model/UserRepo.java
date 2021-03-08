package jupiterpapi.user.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserDB,String> {
    UserDB findByName(String name);
}
