package com.openhub.authmicroservice.models;

import lombok.Data;

@Data
public class SuccessResponse {
    private String status;
    private String message;
}
