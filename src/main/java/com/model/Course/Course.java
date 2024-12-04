package com.model.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class Course {
    private String name;
    private String title;
    private String description;
    private String categoria;
    private String nivel;
    private double rating;
}
