package com.ToDo_App.config;

import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.dto.ErrorResponseDto;
import com.ToDo_App.dto.user.response.UserDto;
import com.ToDo_App.services.impl.IJWTService;
import com.ToDo_App.utils.AuthenticationContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
@Component
public class JWTAuthenticationFilter implements Filter {

    private final IJWTService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public JWTAuthenticationFilter(IJWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                String username = jwtService.extractUserName(jwtToken, true);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UserDto userDto = new UserDto();
                userDto.setUsername(user.getUserName());
                if (jwtService.isTokenValid(jwtToken, userDto, true)) {
                    AuthenticationContext.setCurrentUser(user);
                }
            } catch (Exception e) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                ErrorResponseDto errorResponse = new ErrorResponseDto(
                        httpRequest.getRequestURI(),
                        HttpStatus.UNAUTHORIZED,
                        "Invalid or expired token",
                        LocalDateTime.now()
                );
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(errorResponse.toJson());
                return;
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            AuthenticationContext.clear();
        }
    }
}

