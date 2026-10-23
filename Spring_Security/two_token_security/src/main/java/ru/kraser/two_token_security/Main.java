package ru.kraser.two_token_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kraser.two_token_security.model.Role;
import ru.kraser.two_token_security.model.User;
import ru.kraser.two_token_security.service.UserService;

import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> users = List.of("admin", "technician", "employee");

        for (String username : users) {
            Role role = getRole(username);
            User user = User.builder()
                    .id(username)
                    .username(username)
                    .enabled(true)
                    .password(passwordEncoder.encode("1234"))
                    .role(role)
                    .build();
            userService.createDefaultUsers(user);
        }
    }

    private static Role getRole(String username) {
        Role role;
        switch (username) {
            case "admin" -> role = Role.ADMIN;
            case "technician" -> role = Role.TECHNICIAN;
            default -> role = Role.EMPLOYEE;
        }
        return role;
    }

}
