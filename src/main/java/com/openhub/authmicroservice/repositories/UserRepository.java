package com.openhub.authmicroservice.repositories;

import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.models.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.UserID = :userID")
    Optional<User> findByUserID(@Param("userID") String userID);

    @Query("SELECT new com.openhub.authmicroservice.models.UserDTO(u.UserID, u.username, u.email) FROM User u WHERE u.UserID = :userID")
    Optional<UserDTO> findAndFilterByUserID(@Param("userID") String userID);
}
