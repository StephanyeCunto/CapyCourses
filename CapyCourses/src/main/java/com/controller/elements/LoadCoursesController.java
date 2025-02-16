package com.controller.elements;

import com.dao.StudentCourseDAO;
import com.dao.StudentDAO;
import com.dto.PaginaPrincipalDTO;
import com.model.elements.Course.Course;
import com.model.elements.Course.CourseReader;
import com.model.elements.Course.CourseSettings;
import com.model.login_cadastro.Student;
import com.model.student.StudentCourse;
import com.singleton.UserSession;
import java.util.ArrayList;
import java.util.List;

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
            course.getRating());

        if (settings != null) {
          dto.loadCoursesSettings(
              settings.getDurationTotal(), settings.isCertificate(), "notStarted", 0);
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
