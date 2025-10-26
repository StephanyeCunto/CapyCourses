package com.dao;

import com.factory.DatabaseJPA;
import com.model.elements.Course.Lessons;
import com.model.student.LessonProgress;
import com.model.student.StudentCourse;
import java.util.List;
import javax.persistence.EntityManager;

public class LessonProgressDAO {
  private EntityManager em;

  public LessonProgressDAO() {
    this.em = DatabaseJPA.getInstance().getEntityManager();
  }

  public void salvar(LessonProgress progress, StudentCourse studentCourse) {
    try {
      this.em.getTransaction().begin();

      // Persistir o progresso da aula
      if (progress.getId() == null) {
        this.em.persist(progress);
      } else {
        this.em.merge(progress);
      }

      // Atualizar a lista de progressos no StudentCourse
      studentCourse.getLessonProgresses().add(progress);

      // Atualizar contadores
      studentCourse.updateProgress();

      // Atualizar StudentCourse
      this.em.merge(studentCourse);

      this.em.getTransaction().commit();
    } catch (Exception e) {
      if (this.em.getTransaction().isActive()) {
        this.em.getTransaction().rollback();
      }
      throw e;
    }
  }

  public List<LessonProgress> buscarProgressosPorCursoAluno(StudentCourse studentCourse) {
    return this.em
        .createQuery(
            "FROM LessonProgress lp WHERE lp.studentCourse = :studentCourse", LessonProgress.class)
        .setParameter("studentCourse", studentCourse)
        .getResultList();
  }

  public LessonProgress buscarPorAulaECursoAluno(Lessons lesson, StudentCourse studentCourse) {
    try {
      return this.em
          .createQuery(
              "FROM LessonProgress lp WHERE lp.lesson = :lesson AND lp.studentCourse = :studentCourse",
              LessonProgress.class)
          .setParameter("lesson", lesson)
          .setParameter("studentCourse", studentCourse)
          .getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}
