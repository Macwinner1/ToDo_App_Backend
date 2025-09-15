package com.ToDo_App.services;

import com.ToDo_App.data.models.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> authenticate(String username, String password);
    public Optional<User> getAuthenticatedUser();
}
