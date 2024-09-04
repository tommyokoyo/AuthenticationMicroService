package com.openhub.authmicroservice.services;

import com.openhub.authmicroservice.entities.Post;
import com.openhub.authmicroservice.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findPostByUserID(String userID) {
        return postRepository.findPostByUserID(userID);
    }
}
