package com.controller.elements;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.dto.PaginaPrincipalDTO;
import com.model.elements.MyCourse;
import com.model.elements.Course.Course;
import com.model.elements.Course.CourseReader;
import com.model.elements.Course.CourseSettings;
import com.model.login_cadastro.Student;
import com.singleton.UserSession;
import com.dao.StudentDAO;
import com.dao.StudentCourseDAO;
import com.model.student.StudentCourse;

public class LoadCoursesController {
    private List<StudentCourse> course;

    CourseReader reader = new CourseReader();
    private List<Course> coursesTotais = reader.readCourses();
    private List<PaginaPrincipalDTO> courses = new ArrayList<>();
    
    public List<PaginaPrincipalDTO> loadCourses() {
        StudentDAO studentDAO = new StudentDAO();
        StudentCourseDAO studentCourseDAO = new StudentCourseDAO();
        Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
        course = studentCourseDAO.buscarCursosPorAluno(student);
        for (int i = 0; i < coursesTotais.size(); i++) {
            if (checkCourses(coursesTotais.get(i))) {
                Course course = coursesTotais.get(i);
                CourseSettings settings = course.getCourseSettings();
                
                PaginaPrincipalDTO dto = new PaginaPrincipalDTO();
                
                dto.loadCourses(
                    course.getName(), 
                    course.getTitle(), 
                    course.getDescription(),
                    course.getCategoria(), 
                    course.getNivel(), 
                    course.getRating()
                );

                if (settings != null) {
                    dto.loadCoursesSettings(
                        settings.getDateEnd(),
                        settings.getDurationTotal(),
                        settings.isDateEnd(),
                        settings.isCertificate(),
                        "notStarted",
                        0
                    );
                }
                
                courses.add(dto);
            }
        }
        return courses;
    }

    private boolean checkCourses(Course courseCheck) {
        for (int i = 0; i < course.size(); i++) {
            StudentCourse courseT = course.get(i);
            Course course = courseT.getCourse();
            if (courseCheck.getTitle().equals(course.getTitle())) {
                return false;
            }
        }
        return true;
    }
}
