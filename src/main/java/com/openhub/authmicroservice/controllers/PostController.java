package com.openhub.authmicroservice.controllers;

import com.openhub.authmicroservice.entities.Post;
import com.openhub.authmicroservice.entities.User;
import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import com.openhub.authmicroservice.models.PostDTO;
import com.openhub.authmicroservice.models.UserDTO;
import com.openhub.authmicroservice.repositories.PostRepository;
import com.openhub.authmicroservice.security.JWTUtil;
import com.openhub.authmicroservice.services.PostService;
import com.openhub.authmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, UserService userService, JWTUtil jwtUtil, PostRepository postRepository) {
        this.postService = postService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.postRepository = postRepository;
    }

    @GetMapping("/get-all-posts")
    public ResponseEntity<?> getaAllUserPosts(@RequestHeader(value = "Authorization", required = false) String authToken) {
        // Pull the UserID from the Authorization Token
        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);

            // Find Username
            try {
                String UserID = jwtUtil.extractUserID(authToken);
                Optional<UserDTO> user = userService.findByUserID(UserID);
                if (user != null && user.isPresent()) {
                    try {
                        UserDTO userDetails = user.get();
                        try {
                            // Get User Posts
                            List<Post> userPosts = postService.findPostByUserID(userDetails.getUserID());

                            // Check if User has any posts
                            if (userPosts != null && !userPosts.isEmpty()) {
                                return ResponseUtil.buildSuccessResponse(
                                        HttpStatus.OK,
                                        "Success",
                                        "Posts retrieved"
                                );
                            } else {
                                return ResponseUtil.buildSuccessResponse(
                                        HttpStatus.OK,
                                        "Success",
                                        "Tsk Tsk Tsk...User has not Posted yet"
                                );
                            }
                        }
                        catch (Exception e) {
                            System.out.println("Error Fetching User Posts: " + e.getMessage());
                            return ResponseUtil.buildErrorResponse(
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    "Error",
                                    "Error Processing Request"
                            );
                        }
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        return ResponseUtil.buildErrorResponse(
                                HttpStatus.OK,
                                "Error",
                                "Error Procession request: " + e.getMessage()
                        );
                    }
                } else {
                    return ResponseUtil.buildErrorResponse(HttpStatus.FORBIDDEN,
                            "Error",
                            "You are not authorized to access this resource.");
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseUtil.buildErrorResponse(
                        HttpStatus.UNAUTHORIZED,
                        "Error Processing request",
                        e.getMessage()
                );
            }
        } else {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.FORBIDDEN,
                    "Error",
                    "You are not authorized to access this resource."
            );
        }
    }

    @PostMapping("/new-post")
    public ResponseEntity<?> newPost(@RequestHeader(value = "Authorization", required = false) String authToken,
                                     @RequestBody PostDTO postDTO) {
        String postTitle = postDTO.getPostTitle();
        System.out.println(postTitle);
        // Pull the UserID from the Authorization Token
        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);

            String UserID = jwtUtil.extractUserID(authToken);
            Optional<User> user = userService.findUserByUserID(UserID);
            System.out.println(postDTO.getPostTitle());
            if (user != null && user.isPresent()) {
                try {
                    if (postDTO != null) {
                        // Save post
                        Post newPost = new Post();
                        newPost.setPostID(jwtUtil.generateUUID());
                        newPost.setTitle(postDTO.getPostTitle());
                        newPost.setContent(postDTO.getPostContent());
                        newPost.setUser(user.get());
                        postRepository.save(newPost);

                        return ResponseUtil.buildSuccessResponse(
                                HttpStatus.CREATED,
                                "Success",
                                "Post Created"
                        );
                    } else {
                        return ResponseUtil.buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                "Error",
                                "No null values man"
                        );
                    }


                } catch (Exception e) {
                    System.out.println("Error creating User Posts: " + e.getMessage());
                    return ResponseUtil.buildErrorResponse(
                            HttpStatus.NOT_ACCEPTABLE,
                            "Error",
                            "Error Processing Request"
                    );
                }
            } else {
                return ResponseUtil.buildErrorResponse(
                        HttpStatus.FORBIDDEN,
                        "Error",
                        "You are not authorized to access this resource."
                );
            }

        } else {
            return ResponseUtil.buildErrorResponse(
                    HttpStatus.FORBIDDEN,
                    "Error",
                    "You are not authorized to access this resource."
            );
        }
    }
}

