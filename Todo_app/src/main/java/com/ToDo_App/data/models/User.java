package com.ToDo_App.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;

}
