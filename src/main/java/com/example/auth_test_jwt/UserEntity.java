package com.example.auth_test_jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    private String username;
    private String password;
    private String role;
}
