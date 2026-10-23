package ru.kraser.two_token_security.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kraser.two_token_security.model.User;
import ru.kraser.two_token_security.service.UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityAppConfiguration {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.getUserByName(username);
                if (user == null) throw new UsernameNotFoundException("Неверное имя пользователя или пароль !!!");
                return new JwtUserDetails(user);
            }
        };
    }

//    Если бина AuthenticationProvider нет, но есть бин UserDetailsService,
//    Spring автоматически создаёт и настраивает DaoAuthenticationProvider на его основе

//    Если по какой-то причине нужно оставить бин AuthenticationProvider (например, для более сложной настройки)
//    Необходимо убрать @Bean с этого метода - public UserDetailsService userDetailsService()

/*    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
