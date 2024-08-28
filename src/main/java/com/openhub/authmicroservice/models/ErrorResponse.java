package com.openhub.authmicroservice.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private String status;
    private String message;
}
