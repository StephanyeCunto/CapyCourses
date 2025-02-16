package com.dao;

import com.factory.DatabaseJPA;
import com.model.login_cadastro.User;
import com.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDAO implements IDao<User> {
  private EntityManager em;

  public UserDAO() {
    this.em = DatabaseJPA.getInstance().getEntityManager();
  }

  @Override
  public void salvar(User obj) {
    try {
      this.em.getTransaction().begin();
      this.em.persist(obj);
      this.em.getTransaction().commit();
    } catch (Exception e) {
      if (this.em.getTransaction().isActive()) {
        this.em.getTransaction().rollback();
      }
      throw e;
    }
  }

  @Override
  public void editar(User obj) {
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
  public boolean deletar(User obj) {
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
  public User buscar(int id) {
    return this.em.find(User.class, id);
  }

  @Override
  public List<User> buscarTodos() {
    return this.em.createQuery("FROM User", User.class).getResultList();
  }

  public User buscarPorEmail(String email) {
    try {
      String jpql = "SELECT u FROM User u WHERE u.email = :email";
      return this.em.createQuery(jpql, User.class).setParameter("email", email).getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void atualizar(User user) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(user);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }
}
