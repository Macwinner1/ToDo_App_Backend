package com.ToDo_App.services;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    UserDto loginUser(LoginRequestDto loginRequestDto);
    UserDto registerUser(RegisterRequestDto registerRequestDto);
    boolean checkUserExists(String username, String email);
    User getAuthenticatedUser(HttpSession session);
    void logout();
}
