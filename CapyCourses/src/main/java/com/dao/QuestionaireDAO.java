package com.dao;

import com.model.elements.Course.Questionaire;
import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class QuestionaireDAO {
    public void save(Questionaire questionaire) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(questionaire);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
} 