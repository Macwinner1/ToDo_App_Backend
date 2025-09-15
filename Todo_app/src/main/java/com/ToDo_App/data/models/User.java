package com.ToDo_App.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "todoapp_users")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ToDo> toDos = new HashSet<>();

}
