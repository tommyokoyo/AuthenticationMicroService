package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import com.openhub.authmicroservice.models.User;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authService;

    @Autowired
    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> validateCredentials(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()){
            return ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", "Username can not be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            return ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", "Password can not be null or empty");
        }
        if (authService.validateUser(user.getUsername(), user.getPassword())){
            return ResponseUtil.buildSuccessResponse(HttpStatus.OK, "Login Successful", authService.getToken());
        } else {
            return ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", "User or Password is Incorrect");
        }
    }
}
