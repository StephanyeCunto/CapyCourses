package com.model.auth;

import java.io.*;

import javax.persistence.EntityManager;

import com.model.auth.entity.UserEntity;
import com.model.factory.DatabaseJPA;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Register {
    public void register(UserEntity user){
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    
    }
}
