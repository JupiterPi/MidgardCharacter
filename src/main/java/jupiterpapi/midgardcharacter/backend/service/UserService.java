package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.UserCreateDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;

import java.util.Collection;

public interface UserService {
    Collection<UserDTO> getUsers();

    UserDTO postUser(UserCreateDTO user);
}
