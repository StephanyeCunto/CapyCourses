package com.controller.student;

import com.UserSession;
import com.model.student.MyCourse;
import com.model.student.Student;
import com.model.Course.Course;
import com.model.Course.CourseReader;
import com.model.student.MyCourse;
import java.util.*;

import lombok.*;

public class LoadCoursesController01{
    private List<Course> courses;

    private List<MyCourse> loadMyCourses(Student student){
            return student.getCourse();
    }

    private List<Course> loadNotMyCourses(Student student){
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

    public List<Course> loadCoursesTotais(){
        CourseReader reader = new CourseReader();
        return reader.readCourses();
    }

    public List<String> loadMyCourses(){
        Student student = new Student(UserSession.getInstance().getUserEmail());
        return student.getTitleCourse();
    }
}
