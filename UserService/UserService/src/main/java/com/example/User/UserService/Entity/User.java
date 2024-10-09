package com.example.User.UserService.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    private String name;
    private String Password;
    private String email;
    private String phoneNumber;

    public User(int id, String userName, String password, String email, String phoneNumber) {
        this.id = id;
        this.name = userName;
        Password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
