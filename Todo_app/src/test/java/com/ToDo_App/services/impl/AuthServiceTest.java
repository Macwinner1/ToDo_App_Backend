package com.ToDo_App.services.impl;

import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.user.UserDto;
import com.ToDo_App.dto.user.request.LoginRequestDto;
import com.ToDo_App.dto.user.request.RegisterRequestDto;
import com.ToDo_App.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;

    @Test
    public void testRegisterUser() {
        RegisterRequestDto user = new RegisterRequestDto();
        user.setUsername("macwinner");
        user.setPassword("12345678");
        user.setEmail("ifechukwuw@gmail.com");
        user.setFirstName("ifechukwu");
        user.setLastName("Okafor");

        UserDto registerUser = authService.registerUser(user, httpSession);
        assertEquals(registerUser.getUsername(), user.getUsername());
    }

    @Test
    public void testLoginUser() {
        RegisterRequestDto user = new RegisterRequestDto();
        user.setUsername("macwinner");
        user.setPassword("12345678");
        user.setEmail("ifechukwuw@gmail.com");
        user.setFirstName("ifechukwu");
        user.setLastName("Okafor");

        authService.registerUser(user, httpSession);

        LoginRequestDto newUser = new LoginRequestDto();
        newUser.setUsername("macwinner");
        newUser.setPassword("12345678");

        UserDto loginUser = authService.loginUser(newUser, httpSession);
        assertTrue(userRepository.findByUsername(loginUser.getUsername()).isPresent());
    }

}