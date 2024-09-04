package com.openhub.authmicroservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "tb_posts",
        uniqueConstraints = {
                @UniqueConstraint(name = "post_id_constraint", columnNames = "post_id"),
                @UniqueConstraint(name = "user_id_constraint", columnNames = "user_id")}
)
public class Post {
    @Id
    @Column(
            name = "post_id",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(150)"
    )
    private String PostID;

    @Column(
            name = "post_title",
            nullable = false,
            columnDefinition = "VARCHAR(150)"
    )
    private String Title;

    @Column(
            name = "post_content",
            nullable = false,
            columnDefinition = "VARCHAR(500)"
    )
    private String Content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
