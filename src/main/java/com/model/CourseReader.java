package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.model.Course.Course;

public class CourseReader {
    public List<Course> readCourses() {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_curso.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                String title = values[1];
                String description = values[2];
                String categoria = values[3];
                String nivel = values[4];
                double rating = Double.parseDouble(values[6]);

                Course course = new Course(name, title, description, categoria, nivel, rating);
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
