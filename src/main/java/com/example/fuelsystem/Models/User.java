package com.example.fuelsystem.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter@Setter
public class User {


    @Column(unique = true)
    private String username;
    private String passwordSalt;
    private String passwordHash;
    private Role role;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @GeneratedValue
    private Long id;


    public User(){}


    public User(String username, String password, Role role)
{
        this.username = username;
        this.passwordSalt= RandomStringUtils.random(32);
        this.passwordHash= DigestUtils.sha1Hex(password+passwordSalt);
        this.role = role;

    }


    public boolean checkPassword(String password) {
        // Hash the provided password with the stored salt
        String hashedPassword = DigestUtils.sha1Hex(password + getPasswordSalt());

        // Compare the hashed password with the stored hashed password
        return hashedPassword.equals(getPasswordHash());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
