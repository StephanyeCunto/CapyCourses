package com.controller.student;

import com.model.student.MyCourse;
import com.model.student.Student;
import com.model.Course.Course;
import java.util.*;

import lombok.*;

public class LoadCourses{
    private List<Course> courses;

    public List<MyCourse> loadMyCourses(Student student){
            return student.getCourse();
    }

    public List<Course> loadNotMyCourses(Student student){
        List<MyCourse> myCourse=loadMyCourses(student);
        List<Course> notMyCourse = new ArrayList<>();
        for(int i=0; i<myCourse.size(); i++){
            for(int j=0; j<courses.size(); j++){
                if(!myCourse.get(i).getCourse().getTitle().equals(courses.get(j).getTitle())){
                    notMyCourse.add(courses.get(j));
                }
            }
        }
        return notMyCourse;
    }
}