package demo.Shift_Managar.service;

import demo.Shift_Managar.dto.RegisterRequest;
import demo.Shift_Managar.dto.UserDTO;
import demo.Shift_Managar.model.User;
import demo.Shift_Managar.model.UserRole;
import demo.Shift_Managar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Register new user
    public User registerUser(RegisterRequest request, UserRole role) throws Exception {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email already registered");
        }
        
        User user = new User(
            request.getEmail(),
            request.getPassword(), // In production, use BCryptPasswordEncoder
            request.getFullName(),
            request.getPhoneNumber(),
            role
        );
        
        return userRepository.save(user);
    }
    
    // Authenticate user
    public Optional<User> authenticateUser(String email, String password) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        throw new Exception("Invalid email or password");
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // Get all employees
    public List<UserDTO> getAllEmployees() {
        return userRepository.findByRole(UserRole.EMPLOYEE)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get all admins
    public List<UserDTO> getAllAdmins() {
        return userRepository.findByRole(UserRole.ADMIN)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get all active users
    public List<UserDTO> getAllActiveUsers() {
        return userRepository.findByActive(true)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Update user
    public User updateUser(Long id, User userDetails) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found"));
        
        if (userDetails.getFullName() != null) {
            user.setFullName(userDetails.getFullName());
        }
        if (userDetails.getPhoneNumber() != null) {
            user.setPhoneNumber(userDetails.getPhoneNumber());
        }
        if (userDetails.getActive() != null) {
            user.setActive(userDetails.getActive());
        }
        
        return userRepository.save(user);
    }
    
    // Delete user (soft delete)
    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }
    
    // Convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getActive()
        );
    }
}
