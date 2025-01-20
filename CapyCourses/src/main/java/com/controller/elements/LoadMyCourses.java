package com.controller.elements;

import com.model.elements.Course.Course;
import com.model.elements.Course.CourseReader;
import com.model.elements.Course.CourseSettings;
import com.model.login_cadastro.Student;
import com.singleton.UserSession;
import com.dto.PaginaPrincipalDTO;
import com.dao.StudentDAO;
import com.dao.StudentCourseDAO;
import com.model.student.StudentCourse;
import com.dao.CourseDAO;

import java.util.*;

public class LoadMyCourses {
    private List<StudentCourse> course;
    private List<PaginaPrincipalDTO> courses = new ArrayList<>();
    private StudentCourseDAO studentCourseDAO = new StudentCourseDAO();

    public List<PaginaPrincipalDTO> loadMyCourses() {
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
        course = studentCourseDAO.buscarCursosPorAluno(student);
        for (int i = 0; i < course.size(); i++) {
            StudentCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
            dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                    course.getNivel(), course.getRating());

            CourseSettings courseSettings = course.getCourseSettings();
            dto.loadCoursesSettings(
                courseSettings != null ? courseSettings.getDurationTotal() : "",
                courseSettings != null ? courseSettings.isCertificate() : false,
                courseT.getStatus(),
                courseT.getProgress()
            );
            courses.add(dto);
        }
        return courses;
    }

    public List<PaginaPrincipalDTO> loadMyCoursesStarted() {
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
        course = studentCourseDAO.buscarCursosPorAluno(student);
        for (int i = 0; i < course.size(); i++) {
            StudentCourse courseT = course.get(i);
            Course course = courseT.getCourse();
            if (courseT.getStatus().equals("started")) {
                PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
                dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                        course.getNivel(), course.getRating());

                CourseReader courseReader = new CourseReader();
                CourseSettings courseSettings = courseReader.courseSettings(course.getTitle());
                dto.loadCoursesSettings( courseSettings.getDurationTotal(), courseSettings.isCertificate(), courseT.getStatus(),courseT.getProgress());
                courses.add(dto);
            }
        }
        return courses;
    }

    public List<PaginaPrincipalDTO> loadMyCoursesCompleted() {
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
        course = studentCourseDAO.buscarCursosPorAluno(student);
        for (int i = 0; i < course.size(); i++) {
            StudentCourse courseT = course.get(i);
            Course course = courseT.getCourse();

            if (courseT.getStatus().equals("completed")) {
                PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
                dto.loadCourses(course.getName(), course.getTitle(), course.getDescription(), course.getCategoria(),
                        course.getNivel(), course.getRating());

                CourseReader courseReader = new CourseReader();
                CourseSettings courseSettings = courseReader.courseSettings(course.getTitle());
                dto.loadCoursesSettings(courseSettings.getDurationTotal(), courseSettings.isCertificate(), courseT.getStatus(),100);
                courses.add(dto);
            }
        }
        return courses;
    }

    public List<PaginaPrincipalDTO> loadProfessorCourses() {
        List<PaginaPrincipalDTO> courses = new ArrayList<>();
        String professorEmail = UserSession.getInstance().getUserEmail();
        
        CourseDAO courseDAO = new CourseDAO();
        List<Course> professorCourses = courseDAO.buscarCursosPorProfessor(professorEmail);
        
        for (Course course : professorCourses) {
            PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
            
            dto.loadCourses(
                course.getName(), 
                course.getTitle(), 
                course.getDescription(),
                course.getCategoria(), 
                course.getNivel(), 
                course.getRating()
            );

            CourseSettings settings = course.getCourseSettings();
            if (settings != null) {
                dto.loadCoursesSettings(
                    settings.getDurationTotal(),
                    settings.isCertificate(),
                    "professor",
                    100
                );
            }
            
            courses.add(dto);
        }
        
        return courses;
    }
}
