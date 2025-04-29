package com.example.userservice.service;

import com.example.userservice.dto.UserRegistrationRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.Role;
import com.example.userservice.entity.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new RuntimeException("User with this login already exists");
        }

        Role userRole = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Default user role not found"));

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(userRole);
        user.setBlocked(false);

        user = userRepository.save(user);
        return convertToUserResponse(user);
    }

    public UserResponse getUserByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.isBlocked()) {
            throw new RuntimeException("User is blocked");
        }

        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setFullName(user.getFullName());
        response.setLogin(user.getLogin());
        
        UserResponse.RoleResponse roleResponse = new UserResponse.RoleResponse();
        roleResponse.setName(user.getRole().getName());
        roleResponse.setDescription(user.getRole().getDescription());
        response.setRole(roleResponse);

        return response;
    }
} 