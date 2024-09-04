package com.openhub.authmicroservice.services;

import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.repositories.UserRepository;
import com.openhub.authmicroservice.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User createUser(User user) {
        User newUser = new User();
        newUser.setUserID(jwtUtil.generateUUID());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
        return newUser;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByUserID(String UserID) {
        return userRepository.findByUserID(UserID);
    }

    public Optional<UserDTO> findByUserID(String userID) {
        return userRepository.findAndFilterByUserID(userID);
    }
}
