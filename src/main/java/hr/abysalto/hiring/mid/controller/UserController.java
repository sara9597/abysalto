package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.LoginRequest;
import hr.abysalto.hiring.mid.dto.UserRegistrationRequest;
import hr.abysalto.hiring.mid.model.User;
import hr.abysalto.hiring.mid.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user registration, authentication, and management")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }

        User user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        try {
            User user = userService.authenticateUser(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                throw new NoSuchElementException("User not found with ID: " + id);
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
    }
    
    @GetMapping("/current")
    @Operation(summary = "Get current user information")
    public ResponseEntity<User> getCurrentUser(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                throw new NoSuchElementException("User not found with username: " + username);
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new NoSuchElementException("User not found with username: " + username);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.updateUser(id, request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 