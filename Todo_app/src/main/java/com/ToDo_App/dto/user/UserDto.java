package com.ToDo_App.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID userId;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, message = "The length of the username should be at least 4")
    private String username;

    @NotEmpty(message = "First Name cannot be empty")
    @Size(min = 2, message = "The length of the first name should be at least 2")
    private String firstName;

    @Size(min = 2, message = "The length of the last name should be at least 2")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}