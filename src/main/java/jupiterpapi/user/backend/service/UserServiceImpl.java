package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;
import jupiterpapi.user.backend.model.UserCreateDTO;
import jupiterpapi.user.backend.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserUIMapper mapper;

    @Autowired
    UserDBService db;

    public List<UserDTO> getUsers() {
        List<User> users = db.getUser();
        return users.stream().map(user -> mapper.map(user)).collect(Collectors.toList());
    }

    public UserDTO postUser(UserCreateDTO user) {
        return mapper.map(db.postUser(mapper.map(user)));
    }

}
