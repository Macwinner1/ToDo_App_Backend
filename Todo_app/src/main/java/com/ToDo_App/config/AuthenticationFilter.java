package com.ToDo_App.config;

import com.ToDo_App.data.models.User;
import com.ToDo_App.services.UserService;
import com.ToDo_App.services.impl.TokenService;
import com.ToDo_App.utils.AuthenticationContext;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFilter implements Filter {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Optional<User> userOpt = tokenService.validateToken(token);
            userOpt.ifPresent(AuthenticationContext::setCurrentUser);
        }

        try {
            chain.doFilter(request, response);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        } finally {
            AuthenticationContext.clear();
        }
    }
}