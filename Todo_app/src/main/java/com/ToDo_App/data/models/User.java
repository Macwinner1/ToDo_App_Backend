package com.ToDo_App.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column()
    private String userName;


    private String email;

    @Column()
    private String firstName;

    @Column()
    private String lastName;
    @Column
    private String password;

    @Column
    private String confirmPassword;

}
