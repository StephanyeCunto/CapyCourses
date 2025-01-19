package com.util;

import com.dao.UserDAO;
import com.model.login_cadastro.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PasswordMigration {
    public static void migratePasswords() {
        EntityManager em = JPAUtil.getEntityManager();
        UserDAO userDAO = new UserDAO();
        
        try {
            em.getTransaction().begin();
            
            // Busca todos os usuários
            Query query = em.createQuery("SELECT u FROM User u");
            List<User> users = query.getResultList();
            
            // Migra cada senha
            for (User user : users) {
                String plainPassword = user.getPassword(); // senha atual não criptografada
                user.setPassword(plainPassword); // usa o novo método que aplica BCrypt
                userDAO.atualizar(user);
            }
            
            em.getTransaction().commit();
            System.out.println("Migração de senhas concluída com sucesso!");
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro durante a migração: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        migratePasswords();
    }
} 