package com.dao;

import com.factory.DatabaseJPA;
import com.model.login_cadastro.Student;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;

public class StudentDAO implements IDao<Student> {
    private EntityManager em;

    public StudentDAO() {
        this.em = DatabaseJPA.getInstance().getEntityManager();
    }

    @Override
    public void salvar(Student obj) {
<<<<<<< HEAD
        try {
            this.em.getTransaction().begin();
            this.em.persist(obj);
            this.em.getTransaction().commit();
        } catch (Exception e) {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
            throw e;
=======
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
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
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
        return this.em
                .createQuery("FROM Student", Student.class)
                .getResultList();
    }

    public Student buscarPorUserId(Integer userId) {
<<<<<<< HEAD
        try {
            String jpql = "SELECT s FROM Student s WHERE s.user.id = :userId";
            return this.em.createQuery(jpql, Student.class)
=======
        EntityManager em = null;
        try {
            em = DatabaseJPA.getInstance().getEntityManager();
            String jpql = "SELECT s FROM Student s WHERE s.user.id = :userId";
            return em.createQuery(jpql, Student.class)
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
<<<<<<< HEAD
        }
    }
=======
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
} 