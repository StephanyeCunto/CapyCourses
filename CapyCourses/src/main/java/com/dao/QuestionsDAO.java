package com.dao;

import com.model.elements.Course.Question;
import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class QuestionsDAO {
    public void save(Question question) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(question);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}