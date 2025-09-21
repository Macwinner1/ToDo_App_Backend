package com.ToDo_App.services;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;


public interface AuthService {


    UserDto registerUser(RegisterRequestDto userData);

    UserDto loginUser(LoginRequestDto userDto);

    boolean checkUserExists(String userName, String email);

    User getUserEntity(String username);
}
