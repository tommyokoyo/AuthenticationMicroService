package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() {
        return ResponseUtil.buildSuccessResponse
                (HttpStatus.OK, "Success", "You have successfully reached this endpoint");
    }
}
