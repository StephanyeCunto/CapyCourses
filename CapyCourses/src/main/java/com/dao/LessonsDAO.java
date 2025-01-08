package com.dao;

import java.util.List;

import com.model.elements.Course.Lessons;
import com.util.JPAUtil;

import javax.persistence.EntityManager;

public class LessonsDAO {
    public List<Lessons> findByModuleId(Long moduleId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT l FROM Lessons l WHERE l.module.id = :moduleId ORDER BY l.numberOfLesson";
            return em.createQuery(jpql, Lessons.class)
                    .setParameter("moduleId", moduleId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Lessons lesson) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lesson);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
} 