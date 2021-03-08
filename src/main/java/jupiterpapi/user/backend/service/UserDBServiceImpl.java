package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;
import jupiterpapi.user.backend.model.UserDB;
import jupiterpapi.user.backend.model.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDBServiceImpl implements UserDBService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserDBMapper mapper;

    public void reset() {
        userRepo.deleteAll();
    }

    public List<User> getUser() {
        List<UserDB> dbs = userRepo.findAll();
        return dbs.stream().map(db -> mapper.map(db)).collect(Collectors.toList());
    }

    public User postUser(User user) {
        return mapper.map(userRepo.insert(mapper.map(user)));
    }

}
