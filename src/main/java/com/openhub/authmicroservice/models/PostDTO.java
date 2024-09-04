package com.openhub.authmicroservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private String PostTitle;
    private String PostContent;


    public PostDTO(String postTitle, String postContent) {
        PostTitle = postTitle;
        PostContent = postContent;
    }
}
