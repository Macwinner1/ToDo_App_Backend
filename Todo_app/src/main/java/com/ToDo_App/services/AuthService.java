package com.ToDo_App.services;

import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    UserDto loginUser(LoginRequestDto loginRequestDto, HttpSession session);
    UserDto registerUser(RegisterRequestDto registerRequestDto, HttpSession session);
    boolean checkUserExists(String username, String email);
    UserDto getAuthenticatedUser(HttpSession session);
    void logout(HttpSession session);
}
