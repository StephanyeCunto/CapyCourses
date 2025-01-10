package com.dao;

import com.model.elements.Course.CourseSettings;
import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class CourseSettingsDAO {
    public void save(CourseSettings settings) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(settings);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}