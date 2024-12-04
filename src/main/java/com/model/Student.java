package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Course.Course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String name;
    private List<Course> coursesCompleted;
    private Map<Course, Integer> coursesInitiates;

    public Student(String name){
        this.name = name;
        this.coursesCompleted = new ArrayList<>();
        this.coursesInitiates = new HashMap<>();
    }
}
