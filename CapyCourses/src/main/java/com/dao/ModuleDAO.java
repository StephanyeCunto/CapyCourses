package com.dao;

import com.model.elements.Course.Module;
import com.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class ModuleDAO {
  public List<Module> findByCourseId(Long courseId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      String jpql = "SELECT m FROM Module m WHERE m.course.id = :courseId ORDER BY m.moduleNumber";
      return em.createQuery(jpql, Module.class).setParameter("courseId", courseId).getResultList();
    } finally {
      em.close();
    }
  }

  public void save(Module module) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(module);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
  }
}
