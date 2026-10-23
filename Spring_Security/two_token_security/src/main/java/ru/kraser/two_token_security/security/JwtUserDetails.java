package ru.kraser.two_token_security.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kraser.two_token_security.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {
    private User user;

    public JwtUserDetails(User user) {
        this.user = user;
    }

    public String getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // if User have field Role
        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
