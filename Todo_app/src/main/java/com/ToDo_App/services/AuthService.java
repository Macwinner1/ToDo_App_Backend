package com.ToDo_App.services;

import com.ToDo_App.dto.user.TokenizedUserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;

public interface AuthService {


    TokenizedUserDto registerUser(RegisterRequestDto userData);

    TokenizedUserDto loginUser(LoginRequestDto userDto);

    boolean checkUserExists(String userName, String email);
}
