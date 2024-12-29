package com.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
public class ForumDTO {
    private String author;
    private String title;
    private String description;
    private String category;
    private LocalDateTime dateTime;
    private int topics;

    public void setDate(LocalDateTime date) {
        this.dateTime = date;
    }
}
