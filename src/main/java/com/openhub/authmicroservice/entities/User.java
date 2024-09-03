package com.openhub.authmicroservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(
        name = "tb_users",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_constraint", columnNames = "email"),
                @UniqueConstraint(name = "username_constraint", columnNames = "username"),
        }
)
public class User {
    @Id
    @Column(
            name = "user_id",
            nullable = false,
            updatable = false,
            unique = true,
            columnDefinition = "VARCHAR(100)"
    )
    private String UserID;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(250)"
    )
    private String username;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(300)"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "VARCHAR(250)"
    )
    private String password;

}
