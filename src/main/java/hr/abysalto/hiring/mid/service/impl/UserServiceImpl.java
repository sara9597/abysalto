package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.dto.LoginRequest;
import hr.abysalto.hiring.mid.dto.UserRegistrationRequest;
import hr.abysalto.hiring.mid.model.User;
import hr.abysalto.hiring.mid.repository.UserRepository;
import hr.abysalto.hiring.mid.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
    
    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        
        return userRepository.save(user);
    }
    
    public User authenticateUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return user;
    }
    
    public User updateUser(Integer id, UserRegistrationRequest request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhone(request.getPhone());
            user.setAddress(request.getAddress());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }
    
    public boolean deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }
} 