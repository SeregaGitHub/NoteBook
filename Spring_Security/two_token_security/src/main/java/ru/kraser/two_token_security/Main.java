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

/*
@Modifying
@Query("""
    DELETE FROM RefreshToken rt
    WHERE rt.revoked = true
      AND rt.expiresAt < :threshold
""")
int deleteRevokedOlderThan(@Param("threshold") Instant threshold);

@Scheduled(cron = "0 0 3 * * *")  // удаление каждую ночь в 03:00:00
@Transactional
public void cleanup() {
    // Удаляем отозванные токены, истёкшие более 7 дней назад
    Instant threshold = Instant.now().minus(7, ChronoUnit.DAYS);
    int removed = repository.deleteRevokedOlderThan(threshold);
    log.info("Cleaned up {} old revoked refresh tokens", removed);
}

Аннотация @Scheduled сама по себе ничего не делает, пока в приложении не включён механизм планировщика.
Нужно добавить @EnableScheduling в любой @Configuration-класс (например, в Main или отдельный SchedulingConfig):

@Configuration
@EnableScheduling
public class SchedulingConfig {
}
Или прямо в Main:

@SpringBootApplication
@EnableScheduling
public class Main implements CommandLineRunner { ... }
Без @EnableScheduling метод с @Scheduled просто никогда не будет вызван — Spring проигнорирует аннотацию.
 */

/*
Допустим получены токены access и refresh.
Через минуту refresh токен отправлен на эндпоинт /auth/refresh и получены два новых токена.
Старый access токен всё ещё действителен пока не истечёт его время !!!

                            Если нужно отзывать access немедленно

Есть три подхода, каждый со своей ценой:

1. Укоротить TTL access до 1–5 минут
Самое простое. Окно атаки сокращается до 1–5 минут,
но клиент будет ходить на /auth/refresh каждые пару минут.
Для API с высокой нагрузкой это лишние запросы, но для большинства случаев — приемлемо.

2. Blacklist access-токенов (Redis)
При logout/компрометации кладёте jti (уникальный ID токена, claim Jwts.builder().setId(...))
 в Redis с TTL = оставшееся время жизни токена.
 JwtAuthenticationFilter дополнительно проверяет jti в blacklist.
 Минус — теперь каждый запрос идёт в Redis, что частично убивает stateless.

3. Хранить версию токена в БД / Redis
У пользователя есть поле tokenVersion.
В access-токен кладётся claim ver=N.
При logout/смене пароля/компрометации tokenVersion++.
Фильтр сверяет ver из токена с версией в БД.
Минус — снова запрос к БД на каждый вызов (или кэш).

Все три подхода — это отказ от чистого stateless в пользу безопасности.
Выбор зависит от того, что важнее: производительность или мгновенный отзыв.
 */

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
            Role role = setRole(username);
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

    private static Role setRole(String username) {
        Role role;
        switch (username) {
            case "admin" -> role = Role.ADMIN;
            case "technician" -> role = Role.TECHNICIAN;
            default -> role = Role.EMPLOYEE;
        }
        return role;
    }

}
