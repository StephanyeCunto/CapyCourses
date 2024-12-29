package com.controller.student;

import com.model.student.MyCourse;
import com.model.student.Student;
import com.singleton.UserSession;
import com.Course.Course;
import com.Course.CourseReader;
import com.Course.CourseSettings;
import com.dto.PaginaPrincipalDto;

import java.util.*;

public class LoadMyCourses {
    private List<MyCourse> course;
    private List<PaginaPrincipalDto> courses = new ArrayList<>();

    public List<PaginaPrincipalDto> loadMyCourses() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            PaginaPrincipalDto dto = new PaginaPrincipalDto();
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

    public List<PaginaPrincipalDto> loadMyCoursesStarted() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();
            if (courseT.getStatus().equals("started")) {
                PaginaPrincipalDto dto = new PaginaPrincipalDto();
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

    public List<PaginaPrincipalDto> loadMyCoursesCompleted() {
        Student student = new Student(UserSession.getInstance().getUserEmail());
        course = student.getCourse();
        for (int i = 0; i < course.size(); i++) {
            MyCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            if (courseT.getStatus().equals("completed")) {
                PaginaPrincipalDto dto = new PaginaPrincipalDto();
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
