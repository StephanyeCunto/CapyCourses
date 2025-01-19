package com.service;

import com.dao.StudentCourseDAO;
import com.dao.StudentDAO;
import com.dao.CourseDAO;
import com.model.student.StudentCourse;
import com.model.login_cadastro.Student;
import com.model.elements.Course.Course;
import com.singleton.UserSession;

import java.time.LocalDate;
import java.util.List;

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
        try {
            // Buscar aluno atual
            Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
            if (student == null) {
                throw new RuntimeException("Aluno não encontrado");
            }

            // Buscar curso
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

            studentCourseDAO.salvar(enrollment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StudentCourse> buscarCursosDoAluno() {
        Student student = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
        return studentCourseDAO.buscarCursosPorAluno(student);
    }
} 