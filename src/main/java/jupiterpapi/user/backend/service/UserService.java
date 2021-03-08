package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.UserCreateDTO;
import jupiterpapi.user.backend.model.UserDTO;

import java.util.Collection;

public interface UserService {

    Collection<UserDTO> getUsers();
    UserDTO postUser(UserCreateDTO user);

}
