package com.openhub.authmicroservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;


@Entity
@Table(
        name = "TB_USERS",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "user_id", "username"})
)
public class User {
    @Id
    @Column(
            name = "user_id",
            nullable = false,
            updatable = false,
            columnDefinition = "VARCHAR(100)"
    )
    private String UserID;

    @Column(
            name = "username",
            nullable = false,
            updatable = true,
            columnDefinition = "VARCHAR(250)"
    )
    private String username;

    @Column(
            name = "email",
            updatable = true,
            nullable = false,
            columnDefinition = "VARCHAR(300)"
    )
    private String email;

    @Column(
            name = "password",
            updatable = true,
            nullable = false,
            columnDefinition = "VARCHAR(250)"
    )
    private String password;

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
