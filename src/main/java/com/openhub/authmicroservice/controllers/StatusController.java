package com.openhub.authmicroservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @GetMapping("/hello")
    public ResponseEntity<String> apiHello(){
        return ResponseEntity.ok("Api Hello");
    }
}
