package com.model.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.model.elements.MyCourse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String email;
    private List<MyCourse> course;

    public Student(String email) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_myCourse.csv"))) {
            String line;
            course = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0])) {
                    this.email = values[0];
                    MyCourse myCourse = new MyCourse(values[1]);
                    course.add(myCourse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTitleCourse() {
        List<String> titleCourse = new ArrayList<>();
        for (int i = 0; i < course.size(); i++) {
            titleCourse.add(course.get(i).getCourseTitle());
        }
        return titleCourse;
    }
}
