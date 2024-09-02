package com.openhub.authmicroservice.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String userID;
    private String username;
    private String email;

    public UserDTO(String userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
    }

}
