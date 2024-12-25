package com.model.student;

import com.model.Course.Course;
import com.model.Course.CourseReader;
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
    private String status;
    
    public MyCourse(String title){
        this.courseTitle = title;
        CourseReader courseReader = new CourseReader();
        this.course = courseReader.course(title);
        this.moduleTotal = courseReader.courseModule(title).size();
        for(int i=0; i<this.moduleTotal; i++){
            this.lessonTotal =+ courseReader.courseLessons(title,i).size();
            this.questionaireTotal =+ courseReader.courseQuestionaire(title,i).size();
        }
        this.moduleCompleted = 0;
        this.lessonCompleted = 0;
        this.questionaireCompleted = 0;

        this.courseTotal = this.moduleTotal + this.lessonTotal + this.questionaireTotal;
        this.status = "Started";
    }
}
