package com.pluralsight.pennywise.controllers;

import com.pluralsight.pennywise.models.User;
import com.pluralsight.pennywise.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    //  GET all users
    // GET /users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET one user by id
    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        try {
            Optional<User> foundUser = userService.getUserById(id);

            return foundUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //  CREATE a new user
    // POST /users
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        // stop duplicate usernames before saving
        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username '" + user.getUsername() + "' is already taken"));
        }

        // clients may not choose their own role; admins are promoted directly in the DB
        user.setRole("USER");

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // EDIT an existing user
    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);

            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //  LOGIN
    // POST /users/login
    //reuses the User model for the request body instead of a separate class -
    // only username and password are actually used from it
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser, HttpSession session) {
        try {
            User user = userService.login(loginUser.getUsername(), loginUser.getPassword());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // remember who is logged in for subsequent requests
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // LOGOUT
    // POST /users/logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}