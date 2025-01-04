package com.model.Course;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Module {
    private int moduleNumber;
    private String title;
    private String description;
    private String duration;
    private List<Lessons> aulas;

    public Module(int moduleNumber, String title, String description, String duration, List<Lessons> aulas) {
        this.moduleNumber = moduleNumber;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.aulas = new ArrayList<>();
    }

}