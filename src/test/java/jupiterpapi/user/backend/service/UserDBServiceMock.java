package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;
import jupiterpapi.user.backend.model.UserDB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDBServiceMock implements UserDBService {
    final List<UserDB> users = new ArrayList<>();

    UserDBMapper mapper;

    public void reset() {
        users.clear();
    }

    public List<User> getUser() {
        return users.stream().map(e -> mapper.map(e)).collect(Collectors.toList());
    }

    public User postUser(User user) {
        users.add(mapper.map(user));
        return user;
    }
}
