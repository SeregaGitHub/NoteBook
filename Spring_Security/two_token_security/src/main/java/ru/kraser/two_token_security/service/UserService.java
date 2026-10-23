package ru.kraser.two_token_security.service;

import org.springframework.stereotype.Service;
import ru.kraser.two_token_security.model.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final static Map<String, User> map = new HashMap<>();

    public void createDefaultUsers(User user) {
        map.put(user.getUsername(), user);
    }

    public User getUserByName(String username) {
        return map.get(username);
    }
}
