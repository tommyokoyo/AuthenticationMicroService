package com.openhub.authmicroservice.repositories;

import com.openhub.authmicroservice.entities.Post;
import com.openhub.authmicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p where p.user.UserID = :userID")
    List<Post> findPostByUserID(@Param("userID") String userID);
}
