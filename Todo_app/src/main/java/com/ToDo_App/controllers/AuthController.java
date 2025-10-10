package com.ToDo_App.controllers;

import com.ToDo_App.dto.BaseResponseDto;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.services.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        UserDto userDto = authService.loginUser(loginRequestDto, session);
        return new BaseResponseDto(HttpStatus.OK, "Login successful", userDto);
    }

    @PostMapping("/register")
    public BaseResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto, HttpSession session) {
        UserDto userDto = authService.registerUser(registerRequestDto, session);
        return new BaseResponseDto(HttpStatus.CREATED, "Registration successful", userDto);
    }

    @GetMapping("/me")
    public BaseResponseDto getCurrentUser(HttpSession session) {
        try {
            UserDto userDto = authService.getAuthenticatedUser(session);
            return new BaseResponseDto(HttpStatus.OK, "User fetched successfully", userDto);
        } catch (UserNotAuthenticatedException e) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }
    }

    @PostMapping("/logout")
    public BaseResponseDto logout(HttpSession session) {
        authService.logout(session);
        return new BaseResponseDto(HttpStatus.OK, "Logout successful", null);
    }
}