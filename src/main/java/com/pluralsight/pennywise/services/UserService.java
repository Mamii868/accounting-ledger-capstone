package com.pluralsight.pennywise.services;

import com.pluralsight.pennywise.models.User;
import com.pluralsight.pennywise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  GET
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // CREATE
    public User createUser(User user) {
        // password is saved exactly as it was typed in
        return userRepository.save(user);
    }

    // checks if a username is already taken — used before creating a user
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // EDIT
    public User updateUser(int id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());

        return userRepository.save(existingUser);
    }

    // LOGIN
    public User login(String username, String password) {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            // no user with that username
            return null;
        }

        User user = foundUser.get();

        if (user.getPassword().equals(password)) {
            return user;
        }
        // password didn't match
        return null;
    }
}