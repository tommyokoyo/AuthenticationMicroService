package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import com.openhub.authmicroservice.models.SuccessResponse;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.repositories.UserRepository;
import com.openhub.authmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        return ResponseUtil.buildSuccessResponse(
                HttpStatus.OK,
                "Success",
                "You have successfully reached this endpoint");
    }

    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty");
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Email can not be null or empty"
            );
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Password can not be null or empty"
            );
        }

        User newUser = userService.createUser(user);

        if (newUser != null) {
            return ResponseUtil.buildSuccessResponse(
                    HttpStatus.CREATED,
                    "Success",
                    newUser.getUsername() + " has been created");
        } else {
            return ResponseUtil.buildSuccessResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error",
                    "There has been an error creating the User");
        }
    }
}
