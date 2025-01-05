package com.controller.elements;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadForumJson {
    private String author;
    private String title;
    private String description;
    private String category;
    private String dateTime;
    private int view;
    private int like;
    private int comments;
    private String question;
}
