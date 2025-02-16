package com.dao;

import com.model.elements.Course.Question;
import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class QuestionsDAO {
  public void save(Question question) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      System.out.println("Tentando salvar questão: " + question);
      System.out.println("Questionário associado: " + question.getQuestionaire().getId());

      em.getTransaction().begin();
      em.persist(question);
      em.getTransaction().commit();

      System.out.println("Questão salva com sucesso. ID: " + question.getId());
    } catch (Exception e) {
      System.err.println("Erro ao salvar questão: " + e.getMessage());
      e.printStackTrace();
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw e;
    } finally {
      em.close();
    }
  }
}
