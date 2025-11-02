package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.ToDo;
import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.ToDoRepository;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.todo.ToDoDto;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.exceptions.FieldCantBeEmpty;
import com.ToDo_App.exceptions.UserAlreadyExistsException;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.exceptions.UsernameNotFoundException;
import com.ToDo_App.services.AuthService;
import com.ToDo_App.utils.mapper.ToDoMapper;
import com.ToDo_App.utils.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;
    private final ToDoMapper toDoMapper;

    @Override
    public UserDto loginUser(LoginRequestDto loginRequestDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequestDto.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + loginRequestDto.getUsername());
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid password");
        }
        httpSession.setAttribute("user", user);
        UserDto userDto = userMapper.toUserDto(user);

        List<ToDo> todos = toDoRepository.findByUser(user);
        List<ToDoDto> todoDtos = todos.stream()
                .map(toDoMapper::toToDoDto)
                .collect(Collectors.toList());

        userDto.setToDos(todoDtos);

        return userDto;
    }

    @Override
    public UserDto registerUser(RegisterRequestDto registerRequestDto) {
        if(registerRequestDto.getUsername().trim().isEmpty() || registerRequestDto.getFirstName().trim().isEmpty() || registerRequestDto.getLastName().trim().isEmpty() ) {
            throw new FieldCantBeEmpty("field cannot be empty");
        }

        if (checkUserExists(registerRequestDto.getUsername(), registerRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with username or email");
        }

        User user = userMapper.toUser(registerRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        httpSession.setAttribute("user", user);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }

    @Override
    public User getAuthenticatedUser(HttpSession session) {
        Object userObj = session.getAttribute("user");

        if (userObj == null) {
            throw new UserNotAuthenticatedException("User not authenticated");
        } else if (!(userObj instanceof User)) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }

        return (User) userObj;
    }

//    private User getAuthenticatedUser(HttpSession session) {
//        User userId = (User) session.getAttribute("user");
//        if (userId == null) {
//            throw new UserNotAuthenticatedException("User not authenticated");
//        }
//        return userRepository.findById(UUID.fromString(userId))
//                .orElseThrow(() -> new UserNotAuthenticatedException("User not found with ID: " + userId));
//    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }
}