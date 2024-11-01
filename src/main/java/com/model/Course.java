package com.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class Course {
    private String name;
    private double rating;
    private int hours;
    private String description;
    private String type;
}
