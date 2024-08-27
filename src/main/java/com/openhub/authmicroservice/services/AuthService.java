package com.openhub.authmicroservice.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public Boolean validateUser(String username, String password) {
        return (username.equals("okoyotommy") && password.equals("password"));
    }
}
