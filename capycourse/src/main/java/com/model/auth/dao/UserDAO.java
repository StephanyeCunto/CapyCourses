package com.model.auth.dao;

import com.model.factory.DatabaseJPA;
import com.model.auth.entity.UserEntity;

import javax.persistence.EntityManager;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    public void save(UserEntity user) {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean emailExists(String email) {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email", Long.class)
            .setParameter("email", email).getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }

    public boolean login(String email, String password) {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
            .setParameter("email", email).getSingleResult();

            if (user != null && BCrypt.checkpw(password, user.getPassword()))  return true;
            else return false;
        } catch (javax.persistence.NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }

}
