package com.dto;

import lombok.*;

@Getter
@Setter
public class ForumDTO {
    private String author;
    private String title;
    private String description;
    private String category;
    private String dateTime;
    private int view;
    private int like;
    private int comments;
}
