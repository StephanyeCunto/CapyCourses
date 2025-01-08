package com.dto;

import lombok.*;

@Getter
@Setter
public class BibliotecaDTO {
    private String author;
    private String title;
    private String description;
    private String category;
    private boolean favorite;
}
