package com.dao;

import com.factory.DatabaseJPA;
import com.model.login_cadastro.Teacher;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;

public class TeacherDAO implements IDao<Teacher> {
    private EntityManager entityManager;

    public TeacherDAO() {

        entityManager = DatabaseJPA.getInstance().getEntityManager();

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

    }

    @Override
    public void salvar(Teacher obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;

        EntityManager em = null;
        try {
            em = DatabaseJPA.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void editar(Teacher obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean deletar(Teacher obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Teacher buscar(int id) {
        try {
            return entityManager.find(Teacher.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Teacher> buscarTodos() {
        try {
            return entityManager
                    .createQuery("FROM Teacher", Teacher.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Teacher buscarPorUserId(Integer userId) {

        try {
            String jpql = "SELECT t FROM Teacher t WHERE t.user.id = :userId";
            return entityManager.createQuery(jpql, Teacher.class)

        EntityManager em = null;
        try {
            em = DatabaseJPA.getInstance().getEntityManager();
            String jpql = "SELECT t FROM Teacher t WHERE t.user.id = :userId";
            return em.createQuery(jpql, Teacher.class)

                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
} 