package com.openhub.authmicroservice.models;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
}
