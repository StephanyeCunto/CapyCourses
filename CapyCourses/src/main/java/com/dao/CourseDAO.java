package com.dao;

import com.model.elements.Course.Course;
import com.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager; // mude de jakarta para javax
import javax.persistence.NoResultException;

public class CourseDAO {

  public void save(Course course) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(course);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  public List<Course> findAll() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      return em.createQuery("FROM Course", Course.class).getResultList();
    } finally {
      em.close();
    }
  }

  public Course findById(Long id) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      return em.find(Course.class, id);
    } finally {
      em.close();
    }
  }

  public void update(Course course) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(course);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  public void delete(Course course) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      course = em.merge(course);
      em.remove(course);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  public Course findByTitle(String title) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      // Carrega curso, módulos e questionários em uma única consulta
      Course course =
          em.createQuery(
                  "SELECT DISTINCT c FROM Course c "
                      + "LEFT JOIN FETCH c.modules m "
                      + "LEFT JOIN FETCH m.questionaire q "
                      + "LEFT JOIN FETCH m.lessons "
                      + "LEFT JOIN FETCH q.questions "
                      + "WHERE c.title = :title",
                  Course.class)
              .setParameter("title", title)
              .getSingleResult();

      return course;
    } catch (NoResultException e) {
      return null;
    } finally {
      em.close();
    }
  }

  public void saveAll(List<Course> courses) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      for (Course course : courses) {
        em.persist(course);
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  public List<Course> buscarCursosPorProfessor(String professorEmail) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      return em.createQuery("FROM Course c WHERE c.name = :email", Course.class)
          .setParameter("email", professorEmail)
          .getResultList();
    } finally {
      em.close();
    }
  }
}
