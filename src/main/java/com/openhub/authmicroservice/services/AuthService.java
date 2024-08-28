package com.openhub.authmicroservice.services;

import com.openhub.authmicroservice.models.SuccessResponse;
import com.openhub.authmicroservice.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Boolean validateUser(String username, String password) {
        return (username.equals("okoyotommy") && password.equals("password"));
    }

    public String getToken() {
        return jwtUtil.generateToken("okoyotommy");
    }
}
