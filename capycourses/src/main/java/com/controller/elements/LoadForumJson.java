package com.controller.elements;

import lombok.*;

@Getter
@Setter

public class LoadForumJson {
    private String author;
    private String title;
    private String description;
    private String category;
    private String dateTime;
    private int view;
    private int like;
    private int comments;

    public LoadForumJson(String author, String title, String description, String category, String dateTime, int view, int like, int comments) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.category = category;
        this.dateTime = dateTime;
        this.view = view;
        this.like = like;
        this.comments = comments;
    }
}
