package com.dao;

import com.factory.DatabaseJPA;
import com.model.login_cadastro.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class StudentDAO implements IDao<Student> {
  private EntityManager em;

  public StudentDAO() {
    this.em = DatabaseJPA.getInstance().getEntityManager();
  }

  @Override
  public void salvar(Student obj) {
    try {
      this.em.getTransaction().begin();
      this.em.persist(obj);
      this.em.flush();
      this.em.getTransaction().commit();
    } catch (Exception e) {
      if (this.em.getTransaction().isActive()) {
        this.em.getTransaction().rollback();
      }
      throw e;
    }
  }

  @Override
  public void editar(Student obj) {
    try {
      this.em.getTransaction().begin();
      this.em.merge(obj);
      this.em.getTransaction().commit();
    } catch (Exception e) {
      if (this.em.getTransaction().isActive()) {
        this.em.getTransaction().rollback();
      }
      throw e;
    }
  }

  @Override
  public boolean deletar(Student obj) {
    try {
      this.em.getTransaction().begin();
      obj = this.em.merge(obj);
      this.em.remove(obj);
      this.em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      if (this.em.getTransaction().isActive()) {
        this.em.getTransaction().rollback();
      }
      throw e;
    }
  }

  @Override
  public Student buscar(int id) {
    return this.em.find(Student.class, id);
  }

  @Override
  public List<Student> buscarTodos() {
    return this.em.createQuery("FROM Student", Student.class).getResultList();
  }

  public Student buscarPorUserId(Integer userId) {
    try {
      String jpql = "SELECT s FROM Student s WHERE s.user.id = :userId";
      return this.em
          .createQuery(jpql, Student.class)
          .setParameter("userId", userId)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Student buscarPorEmail(String email) {
    try {
      String jpql = "SELECT s FROM Student s JOIN s.user u WHERE u.email = :email";
      return this.em
          .createQuery(jpql, Student.class)
          .setParameter("email", email)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public EntityManager getEntityManager() {
    return this.em;
  }
}
