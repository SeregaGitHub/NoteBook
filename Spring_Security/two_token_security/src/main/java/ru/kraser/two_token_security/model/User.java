package ru.kraser.two_token_security.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Entity
// @Table(name = "users")
public class User {

//    @Id
//    @GeneratedValue
//    @UuidGenerator(style = UuidGenerator.Style.TIME)
//    @Column(name = "id", length = 36, nullable = false)
    private String id;
//    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;
//    @Column(name = "password", length = 128, nullable = false)
    private String password;
//    @JsonProperty(value = "isEnabled")
//    @Column(name = "enabled", nullable = false)
    private boolean enabled;
//    @Enumerated(EnumType.STRING)
//    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, enabled, role);
    }
}
