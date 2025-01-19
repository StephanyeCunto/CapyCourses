package com.dao;

import com.factory.DatabaseJPA;
import com.model.student.StudentCourse;
import com.model.login_cadastro.Student;
import com.model.elements.Course.Course;

import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;

public class StudentCourseDAO {
    private EntityManager em;

    public StudentCourseDAO() {
        this.em = DatabaseJPA.getInstance().getEntityManager();
    }

    public void salvar(StudentCourse obj) {
        try {
            this.em.getTransaction().begin();
            this.em.persist(obj);
            this.em.getTransaction().commit();
        } catch (Exception e) {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public List<StudentCourse> buscarCursosPorAluno(Student student) {
        return this.em
                .createQuery("FROM StudentCourse sc WHERE sc.student = :student", StudentCourse.class)
                .setParameter("student", student)
                .getResultList();
    }

    public StudentCourse buscarPorAlunoECurso(Student student, Course course) {
        try {
            return this.em
                    .createQuery("FROM StudentCourse sc WHERE sc.student = :student AND sc.course = :course", StudentCourse.class)
                    .setParameter("student", student)
                    .setParameter("course", course)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
} 