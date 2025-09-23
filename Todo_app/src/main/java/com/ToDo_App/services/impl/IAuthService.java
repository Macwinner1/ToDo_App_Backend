package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.exceptions.UserAlreadyExistsException;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.exceptions.UsernameNotFoundException;
import com.ToDo_App.services.AuthService;
import com.ToDo_App.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class IAuthService implements AuthService {

    private final UserRepository userRepository;
    private final BCrypt bCrypt = new BCrypt();

    @Autowired
    public IAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto registerUser(RegisterRequestDto userData) {
        if(checkUserExists(userData.getUsername(), userData.getEmail())) {
            throw new UserAlreadyExistsException("A user with this Username or email already exists.");
        }
        User user = UserMapper.mapToUsers(userData, new User(), bCrypt);
        userRepository.save(user);
        return UserMapper.mapToUserDto(user);

    }

    @Override
    public UserDto loginUser(LoginRequestDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + userDto.getUsername())
        );
        boolean passwordMatch = BCrypt.checkpw(userDto.getPassword(), user.getPassword());
        if(!passwordMatch) {
            throw new UserNotAuthenticatedException("A user with this Username or email already exists.");
        }
        return UserMapper.mapsToUserDto(user);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).isPresent();
    }

    @Override
    public User getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }



}
