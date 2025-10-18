package com.ToDo_App.controllers;

import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.dto.user.response.UserResponseDto;
import com.ToDo_App.exceptions.FieldCantBeEmpty;
import com.ToDo_App.exceptions.InvalidCredentialsException;
import com.ToDo_App.exceptions.UserAlreadyExistsException;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.services.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            UserDto userDto = authService.loginUser(loginRequest);
            UserResponseDto response = new UserResponseDto(HttpStatus.OK, "Login successful", userDto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InvalidCredentialsException e) {
            UserResponseDto errorResponse = new UserResponseDto(HttpStatus.UNAUTHORIZED, "Invalid credentials", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            UserDto userDto = authService.registerUser(registerRequest);
            UserResponseDto response = new UserResponseDto(HttpStatus.CREATED, "User registered successfully", userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            UserResponseDto errorResponse = new UserResponseDto(HttpStatus.CONFLICT, "User already exists", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpSession session) {
        try {
            User user = authService.getAuthenticatedUser(session);
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            UserResponseDto response = new UserResponseDto(HttpStatus.OK, "User fetched successfully", userDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UserNotAuthenticatedException e) {
            UserResponseDto errorResponse = new UserResponseDto(HttpStatus.UNAUTHORIZED, "User not authenticated", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

//    @GetMapping("/me")
//    public ResponseEntity<?> getCurrentUser(HttpSession session) {
//        try {
//            User user = authService.getAuthenticatedUser(session);
//
//            log.info("Current user is " + user);
//            UserDto userDto = new UserDto();
//            userDto.setUserId(user.getUserId());
//            userDto.setUsername(user.getUsername());
//            userDto.setEmail(user.getEmail());
//
//            return ResponseEntity.ok(userDto);
//        } catch (UserNotAuthenticatedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User not authenticated");
//        }
//    }


    @PostMapping("/logout")
    public UserResponseDto logout() {
        authService.logout();
        return new UserResponseDto(HttpStatus.OK, "Logout successful", null);
    }
}