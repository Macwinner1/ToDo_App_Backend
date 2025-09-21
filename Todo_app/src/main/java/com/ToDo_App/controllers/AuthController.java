package com.ToDo_App.controllers;


import com.ToDo_App.data.models.User;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.exceptions.UserNotAuthenticatedException;
import com.ToDo_App.services.AuthService;
import com.ToDo_App.utils.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto request, HttpSession session) {
        UserDto userDetails = authService.loginUser(request);
        if (userDetails != null) {
            User user = authService.getUserEntity(request.getUsername());
            session.setAttribute("user", user);
            return ResponseEntity.ok("Login successful. Session started.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request, HttpSession session) {
        UserDto isRegistered = authService.registerUser(request);
        if (isRegistered != null) {
            session.setAttribute("user", request.getUsername());
            return ResponseEntity.ok("User registered and session started.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new UserNotAuthenticatedException("User not logged in");
        }
        return ResponseEntity.ok(user.getUserName() + " is logged in");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Session ended. Logged out.");
    }
}
