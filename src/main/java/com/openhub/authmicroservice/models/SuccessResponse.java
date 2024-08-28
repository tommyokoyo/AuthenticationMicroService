package com.openhub.authmicroservice.models;

import lombok.Data;

@Data
public class SuccessResponse implements response {
    private String status;
    private String message;
}
