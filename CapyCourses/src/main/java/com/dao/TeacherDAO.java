package com.dao;

import com.factory.DatabaseJPA;
import com.model.login_cadastro.Teacher;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;

public class TeacherDAO implements IDao<Teacher> {
    private EntityManager em;

    public TeacherDAO() {
        this.em = DatabaseJPA.getInstance().getEntityManager();
    }

    @Override
    public void salvar(Teacher obj) {
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
    public void editar(Teacher obj) {
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
    public boolean deletar(Teacher obj) {
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
    public Teacher buscar(int id) {
        return this.em.find(Teacher.class, id);
    }

    @Override
    public List<Teacher> buscarTodos() {
        return this.em
                .createQuery("FROM Teacher", Teacher.class)
                .getResultList();
    }

    public Teacher buscarPorUserId(Integer userId) {
        try {
            String jpql = "SELECT t FROM Teacher t WHERE t.user.id = :userId";
            return this.em.createQuery(jpql, Teacher.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
} 
 