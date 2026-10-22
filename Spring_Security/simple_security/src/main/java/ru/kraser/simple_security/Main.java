package ru.kraser.simple_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kraser.simple_security.model.Role;
import ru.kraser.simple_security.model.User;
import ru.kraser.simple_security.service.UserService;

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
            User user = User.builder()
                    .id(username)
                    .username(username)
                    .enabled(true)
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .build();
            userService.createDefaultUsers(user);
        }
    }
}
