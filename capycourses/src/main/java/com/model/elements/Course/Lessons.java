package com.model.elements.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Lessons {
    private String title;
    private String videoLink;
    private String details;
    private String materials;
    private String duration;
    String module;
    int numberOfModule;
    int numberOfLesson;
}
