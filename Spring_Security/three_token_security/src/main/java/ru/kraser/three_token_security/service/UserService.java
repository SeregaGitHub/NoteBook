package ru.kraser.three_token_security.service;

import org.springframework.stereotype.Service;
import ru.kraser.three_token_security.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private static final Map<String, User> map = new ConcurrentHashMap<>();

    public void createDefaultUsers(User user) {
        map.put(user.getUsername(), user);
    }

    public User getUserByName(String username) {
        return map.get(username);
    }
}
