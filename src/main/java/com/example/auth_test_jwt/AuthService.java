package com.example.auth_test_jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;

    public String signup(String username, String password) {
        if (repo.existsById(username)) return "Username taken";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole("ROLE_USER");
        repo.save(user);
        return "Signup success";
    }

    public UserDetails login(String username, String password) {
        UserEntity user = repo.findById(username).orElseThrow();
        if (!encoder.matches(password, user.getPassword())) throw new RuntimeException("Bad creds");

        return new User(
            user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
