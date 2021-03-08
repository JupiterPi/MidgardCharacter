package jupiterpapi.midgardcharacter.backend.controller;

import jupiterpapi.midgardcharacter.backend.model.create.UserCreateDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;
import jupiterpapi.midgardcharacter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(path = UserController.PATH)
@RestController
public class UserController implements UserService {
    public static final String PATH = "/api/user";

    @Autowired
    UserService service;

    @GetMapping("")
    public Collection<UserDTO> getUsers() {
        return service.getUsers();
    }

    @PostMapping("")
    public UserDTO postUser(@RequestBody UserCreateDTO user) {
        return service.postUser(user);
    }

}
