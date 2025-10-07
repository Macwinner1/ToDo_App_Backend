package com.ToDo_App.dto.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}