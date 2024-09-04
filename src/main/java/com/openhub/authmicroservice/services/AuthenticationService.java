package com.openhub.authmicroservice.services;

import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthenticationService(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public UserDTO validateUser(String username, String password) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            UserDTO userDetails = new UserDTO(user.get().getUserID(), user.get().getUsername(), user.get().getEmail());
            if (password.equals(user.get().getPassword())) {
                return userDetails;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getToken(String UserID) {
        return jwtUtil.generateToken(UserID);
    }
}
