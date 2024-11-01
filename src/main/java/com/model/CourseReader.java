package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseReader {
    public List<Course> readCourses() {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\com\\bd_curso.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                String description = values[1];
                int hours = Integer.parseInt(values[2]);
                double rating = Double.parseDouble(values[3]);
                String type = values[4];
                Course course = new Course(name, rating, hours, description, type);
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
