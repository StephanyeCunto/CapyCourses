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

    public CourseSettings courseSettings(String titleVerific) {

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
                if (titleVerific.equals(title)) {
                    dateStart = LocalDate.parse(values[1]);
                    dateEnd = LocalDate.parse(values[2]);
                    durationTotal = values[3];
                    isDateEnd = Boolean.parseBoolean(values[4]);
                    isCertificate = Boolean.parseBoolean(values[5]);
                    isGradeMiniun = Boolean.parseBoolean(values[6]);
                    ComboBoxVisibily = values[7];

                    CourseSettings courseSetting = new CourseSettings(title, dateStart, dateEnd, durationTotal,
                            isDateEnd,
                            isCertificate, isGradeMiniun, ComboBoxVisibily);
                    return courseSetting;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Module> courseModule(String titleVerific) {
        List<Module> modules = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_modulos.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String titleCourse = values[0];
                if (titleVerific.equals(titleCourse)) {
                    int moduleNumber = Integer.parseInt(values[1]);
                    String title = values[2];
                    String duration = values[3];
                    String description = values[4];
                    
                    List<Lessons> lessons = courseLessons(titleCourse, moduleNumber);
                    Module moduleCourse = new Module(moduleNumber, title, description, duration, lessons);
                    modules.add(moduleCourse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public List<Lessons> courseLessons(String titleCourseVerific, int moduleNumberVerific) {
        List<Lessons> lessons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_aulas.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String titleCourse = values[0];
                int moduleNumber = Integer.parseInt(values[2]);
                
                if (titleCourseVerific.equals(titleCourse) && moduleNumberVerific == moduleNumber) {
                    String module = values[1];
                    int numberOfLesson = Integer.parseInt(values[3]);
                    String title = values[4];
                    String videoLink = values[5];
                    String details = values[6];
                    String materials = values[7];
                    String duration = values[8];

                    Lessons lesson = new Lessons(title, videoLink, details, materials, duration, module,
                            moduleNumber, numberOfLesson);
                    lessons.add(lesson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public List<Questionaire> courseQuestionaire(String titleCourseVerific, int moduleNumberVerific){
        List<Questionaire> questionaires = new ArrayList<>();

        return questionaires;
    }

    public Course course(String titleVerific){
        List<Course> courses = readCourses();
        for(int i=0; i<courses.size(); i++){
            if(courses.get(i).getTitle().equals(titleVerific)){
                return courses.get(i);
            }
        }
        return null;
    }
}
