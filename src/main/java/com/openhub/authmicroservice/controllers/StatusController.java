package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import com.openhub.authmicroservice.models.TokenDTO;
import com.openhub.authmicroservice.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {


    private final JWTUtil jwtUtil;

    @Autowired
    public StatusController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/api-hello")
    public ResponseEntity<?> apiHello(){
        return ResponseUtil.buildSuccessResponse(HttpStatus.OK,"Success","hello-back");
    }

    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestBody TokenDTO token) {
        String authToken  = token.getToken();
        if (authToken != null) {
            try {
                if (jwtUtil.validateToken(authToken, jwtUtil.extractUserID(authToken))) {
                    return ResponseUtil.buildSuccessResponse(HttpStatus.OK,
                            "Valid Token",
                            "The UserID is " + jwtUtil.extractUserID(authToken));
                } else {
                    return ResponseUtil.buildErrorResponse(HttpStatus.UNAUTHORIZED,
                            "Invalid Token",
                            "We could not verify the token");
                }
            }

            catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                return ResponseUtil.buildErrorResponse(HttpStatus.UNAUTHORIZED,
                        "Invalid Token",
                        e.getMessage());
            }
        }

        return ResponseUtil.buildErrorResponse(HttpStatus.FORBIDDEN,
                "Invalid Token",
                "No Token Provided");
    }
}
