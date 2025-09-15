package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.user.TokenizedUserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.dto.user.response.UserDto;
import com.ToDo_App.expections.UserAlreadyExistsException;
import com.ToDo_App.expections.UsernameNotFoundException;
import com.ToDo_App.services.AuthService;
import com.ToDo_App.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJWTService ijwtService;
    private final UserMapper userMapper;

    @Autowired
    public IAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, IJWTService ijwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ijwtService = ijwtService;
        this.userMapper = userMapper;
    }
    @Override
    public TokenizedUserDto registerUser(RegisterRequestDto userData) {
        if(checkUserExists(userData.getUsername(), userData.getEmail())) {
            throw new UserAlreadyExistsException("A user with this Username or email already exists.");
        }
        User user = userMapper.fromRegisterDto(userData, passwordEncoder);
        userRepository.save(user);
        var jwtToken = ijwtService.generateJwtToken(new UserDto());
        return userMapper.toUserDto(user, jwtToken);

    }

    @Override
    public TokenizedUserDto loginUser(LoginRequestDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + userDto.getUsername())
        );
        boolean passwordMatch = BCrypt.checkpw(user.getPassword(), userDto.getPassword());
        var jwtToken = ijwtService.generateJwtToken(new UserDto());
        return userMapper.toUserDto(user, jwtToken);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).isPresent();
    }
}
