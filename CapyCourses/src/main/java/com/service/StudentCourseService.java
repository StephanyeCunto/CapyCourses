package com.service;

import com.dao.CourseDAO;
import com.dao.StudentCourseDAO;
import com.dao.StudentDAO;
import com.model.elements.Course.Course;
import com.model.login_cadastro.Student;
import com.model.student.StudentCourse;
import com.singleton.UserSession;
import com.util.JPAUtil;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;

public class StudentCourseService {
  private final StudentCourseDAO studentCourseDAO;
  private final StudentDAO studentDAO;
  private final CourseDAO courseDAO;

  public StudentCourseService() {
    this.studentCourseDAO = new StudentCourseDAO();
    this.studentDAO = new StudentDAO();
    this.courseDAO = new CourseDAO();
  }

  public boolean inscreverEmCurso(String courseTitle) {
    EntityManager em = null;
    try {
      em = JPAUtil.getEntityManager();
      em.getTransaction().begin();

      // Buscar aluno atual
      Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
      if (student == null) {
        throw new RuntimeException("Aluno não encontrado");
      }

      // Buscar curso com módulos e aulas
      Course course = courseDAO.findByTitle(courseTitle);
      if (course == null) {
        throw new RuntimeException("Curso não encontrado");
      }

      // Verificar se já está inscrito
      StudentCourse existingEnrollment = studentCourseDAO.buscarPorAlunoECurso(student, course);
      if (existingEnrollment != null) {
        throw new RuntimeException("Aluno já está inscrito neste curso");
      }

      // Criar nova inscrição
      StudentCourse enrollment = new StudentCourse();
      enrollment.setStudent(student);
      enrollment.setCourse(course);
      enrollment.setStartDate(LocalDate.now());
      enrollment.setProgress(0);
      enrollment.setStatus("in_progress");

      // Inicializar contadores
      int totalLessons = course.getModules().stream().mapToInt(m -> m.getLessons().size()).sum();

      int totalQuestionaires =
          (int) course.getModules().stream().filter(m -> m.getQuestionaire() != null).count();

      enrollment.setTotalLessons(totalLessons);
      enrollment.setTotalQuestionaires(totalQuestionaires);
      enrollment.setCompletedLessons(0);
      enrollment.setCompletedQuestionaires(0);

      // Persistir a inscrição
      em.persist(enrollment);
      em.getTransaction().commit();

      return true;

    } catch (Exception e) {
      if (em != null && em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      e.printStackTrace();
      return false;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<StudentCourse> buscarCursosDoAluno() {
    Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
    return studentCourseDAO.buscarCursosPorAluno(student);
  }
}
