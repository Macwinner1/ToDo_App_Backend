package com.ToDo_App.services.impl;


import com.ToDo_App.data.models.User;
import com.ToDo_App.data.repository.UserRepository;
import com.ToDo_App.expections.UserNotAuthenticatedException;
import com.ToDo_App.services.UserService;
import com.ToDo_App.utils.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    @Override
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && BCrypt.checkpw(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getAuthenticatedUser() {
        User user = AuthenticationContext.getCurrentUser();
        if (user == null) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }
        return Optional.of(user);
    }



}