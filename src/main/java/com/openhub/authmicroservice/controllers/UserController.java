package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.repositories.UserRepository;
import com.openhub.authmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        try {
            if (userRepository.count() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
            }

        }
        catch (Exception e) {
            return ResponseUtil.buildSuccessResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error",
                    "Error fetching Users: " + e.getMessage());
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        // Pull username from the SecurityContext holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserID = authentication.getName();

        try {
            Optional<UserDTO> user = userService.findByUserID(UserID);
            if (user != null && user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return ResponseUtil.buildErrorResponse(HttpStatus.FORBIDDEN, "Error", "Error Loading details");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, "Error", e.getMessage());
        }
    }

    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Validates parameters to ensure no null submissions
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Bad Request",
                    "Username can not be null or empty"
            );

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
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseUtil.buildSuccessResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error",
                    "Processing Error: " + e.getMessage());
        }
    }
}
