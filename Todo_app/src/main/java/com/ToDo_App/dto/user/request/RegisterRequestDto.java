package com.ToDo_App.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, message = "The length of the username should be atleast 4")
    private String username;

    @NotEmpty(message = "First Name cannot br empty")
    @Size(min = 2, message = "The length of the first name should be atleast 2")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String password;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String confirmPassword;
}
