package com.model.student;

import com.model.Course.Course;

import lombok.*;

@Getter
@Setter
public class MyCourse {
    private String email;
    private Course course;
    private String courseTitle;
    private int moduleTotal;
    private int moduleCompleted;
    private int lessonTotal;
    private int lessonCompleted;
    private int questionaireTotal;
    private int questionaireCompleted;
    private int courseTotal;

    private MyCourse(Course course){
        this.course = course;
        this.courseTitle = course.getTitle();
        this.moduleTotal = 0;
        this.moduleCompleted = 0;
        this.lessonTotal = 0;
        this.lessonCompleted = 0;
        this.questionaireTotal = 0;
        this.questionaireCompleted = 0;
        this.courseTotal = 0;
    }
}
