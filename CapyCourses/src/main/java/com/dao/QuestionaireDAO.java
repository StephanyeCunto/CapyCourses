package com.dao;

import com.model.elements.Course.Question;
import com.model.elements.Course.Questionaire;
import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class QuestionaireDAO {
    public void save(Questionaire questionaire) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            
            // Primeiro, garantimos que o módulo está gerenciado
            if (questionaire.getModule() != null) {
                if (!em.contains(questionaire.getModule())) {
                    questionaire = em.merge(questionaire);
                }
            }
            
            // Agora persistimos o questionário
            if (!em.contains(questionaire)) {
                em.persist(questionaire);
                em.flush(); // Força a persistência do questionário antes de continuar
            }
            
            // Depois persistimos as questões
            if (questionaire.getQuestions() != null) {
                for (Question question : questionaire.getQuestions()) {
                    if (!em.contains(question)) {
                        question.setQuestionaire(questionaire);
                        em.persist(question);
                    }
                }
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
} 