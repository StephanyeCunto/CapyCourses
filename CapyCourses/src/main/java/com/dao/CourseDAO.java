package com.dao;

import com.model.elements.Course.Course;
import com.util.JPAUtil;

import javax.persistence.EntityManager;  // mude de jakarta para javax
import java.util.List;
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
            String jpql = "SELECT c FROM Course c WHERE c.title = :title";
            return em.createQuery(jpql, Course.class)
                    .setParameter("title", title)
                    .getSingleResult();
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
} 