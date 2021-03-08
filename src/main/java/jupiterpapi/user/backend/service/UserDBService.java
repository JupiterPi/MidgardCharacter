package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;

import java.util.List;

public interface UserDBService {

    List<User> getUser();

    User postUser(User user);

    void reset();
}
