package com.openhub.authmicroservice.models;

import lombok.Data;

@Data
public class SuccessResponse {
    private String success;
    private String message;
}
