package com.model.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public CourseSettings courseSettings(String titleVerific){
        
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_settingsCurso.csv"))) {
            String title;
            LocalDate dateStart;
            LocalDate dateEnd;
            String durationTotal;
            boolean isDateEnd;
            boolean isCertificate;
            boolean isGradeMiniun;
            Object ComboBoxVisibily;
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                title = values[0];
                dateStart = LocalDate.parse(values[1]);
                dateEnd = LocalDate.parse(values[2]);
                durationTotal = values[3];
                isDateEnd = Boolean.parseBoolean(values[4]);
                isCertificate = Boolean.parseBoolean(values[5]);
                isGradeMiniun = Boolean.parseBoolean(values[6]);
                ComboBoxVisibily = values[7];

                        if(titleVerific.equals(title)){
                            CourseSettings courseSetting = new CourseSettings(title, dateStart, dateEnd, durationTotal, isDateEnd,
                            isCertificate, isGradeMiniun, ComboBoxVisibily);
                            return courseSetting;
                        }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}


