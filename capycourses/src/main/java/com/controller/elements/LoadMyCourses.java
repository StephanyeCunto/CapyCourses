package com.controller.elements;

import com.model.Course.Course;
import com.model.Course.CourseReader;
import com.model.Course.CourseSettings;
import com.model.student.MyCourse;
import com.model.student.Student;
import com.singleton.UserSession;
import com.dto.PaginaPrincipalDTO;

import java.util.*;

public class LoadMyCourses {
    private List<MyCourse> course;
    private List<PaginaPrincipalDTO> courses = new ArrayList<>();

    public List<PaginaPrincipalDTO> loadMyCourses() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
            dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                    course.getNivel(), course.getRating());

            CourseReader courseReader = new CourseReader();
            CourseSettings courseSettings = courseReader.courseSettings(course.getTitle());
            dto.loadCoursesSettings(courseSettings.getDateEnd(), courseSettings.getDurationTotal(),
                    courseSettings.isDateEnd(), courseSettings.isCertificate(), courseT.getStatus(),courseT.getPercentual());
            courses.add(dto);
        }
        return courses;
    }

    public List<PaginaPrincipalDTO> loadMyCoursesStarted() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();
            if (courseT.getStatus().equals("started")) {
                PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
                dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                        course.getNivel(), course.getRating());

                CourseReader courseReader = new CourseReader();
                CourseSettings courseSettings = courseReader.courseSettings(course.getTitle());
                dto.loadCoursesSettings(courseSettings.getDateEnd(), courseSettings.getDurationTotal(),
                        courseSettings.isDateEnd(), courseSettings.isCertificate(), courseT.getStatus(),courseT.getPercentual());
                courses.add(dto);
            }
        }
        return courses;
    }

    public List<PaginaPrincipalDTO> loadMyCoursesCompleted() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            if (courseT.getStatus().equals("completed")) {
                PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
                dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                        course.getNivel(), course.getRating());

                CourseReader courseReader = new CourseReader();
                CourseSettings courseSettings = courseReader.courseSettings(course.getTitle());
                dto.loadCoursesSettings(courseSettings.getDateEnd(), courseSettings.getDurationTotal(),
                        courseSettings.isDateEnd(), courseSettings.isCertificate(), courseT.getStatus(),100);
                courses.add(dto);
            }
        }
        return courses;
    }
}
