package com.ToDo_App.services;

import com.ToDo_App.data.models.User;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String username, String password);
}
