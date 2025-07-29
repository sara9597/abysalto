package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.dto.LoginRequest;
import hr.abysalto.hiring.mid.dto.UserRegistrationRequest;
import hr.abysalto.hiring.mid.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<User> getAllUsers();
    
    Optional<User> getUserById(Integer id);
    
    Optional<User> getUserByUsername(String username);
    
    User registerUser(UserRegistrationRequest request);
    
    Optional<User> authenticateUser(LoginRequest request);
    
    User updateUser(Integer id, UserRegistrationRequest request);
    
    boolean deleteUser(Integer id);
} 