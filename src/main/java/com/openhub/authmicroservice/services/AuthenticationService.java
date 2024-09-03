package com.openhub.authmicroservice.services;

import com.openhub.authmicroservice.entities.User;
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

    public Boolean validateUser(String username, String password) {
        Optional<User> user = userService.findByUsername(username);
        return user.filter(userObj -> (username.equals(userObj.getUsername())
                && password.equals(userObj.getPassword()))).isPresent();
    }

    public String getToken() {
        return jwtUtil.generateToken("okoyotommy");
    }
}
