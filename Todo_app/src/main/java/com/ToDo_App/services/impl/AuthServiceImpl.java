package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.exceptions.UserAlreadyExistsException;
import com.ToDo_App.exceptions.UsernameNotFoundException;
import com.ToDo_App.services.AuthService;
import com.ToDo_App.utils.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto loginUser(LoginRequestDto loginRequestDto, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequestDto.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + loginRequestDto.getUsername());
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid password");
        }
        session.setAttribute("userId", user.getUserId().toString());
        session.setAttribute("username", user.getUsername());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto registerUser(RegisterRequestDto registerRequestDto, HttpSession session) {
        if (checkUserExists(registerRequestDto.getUsername(), registerRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with username or email");
        }
        User user = userMapper.toUser(registerRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        session.setAttribute("userId", savedUser.getUserId().toString());
        session.setAttribute("username", savedUser.getUsername());
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }

    @Override
    public UserDto getAuthenticatedUser(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            throw new UsernameNotFoundException("User not authenticated");
        }
        Optional<User> userOptional = userRepository.findById(java.util.UUID.fromString(userId));
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
        return userMapper.toUserDto(userOptional.get());
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}