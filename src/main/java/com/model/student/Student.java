package com.model.student;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String name;
    private List<MyCourse> course;

    public Student(String name){
        this.name = name;
        this.course = new ArrayList<>();
    }
}
