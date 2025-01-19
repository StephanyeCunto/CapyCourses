package com.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.dto.ForumDTO;
import com.dto.ForumComentarioDTO;
import com.model.entity.Forum;
import com.model.entity.ForumComment;
import com.util.JPAUtil;

public class ForumDatabase {

    public void createForum(ForumDTO forumDTO) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            Forum forum = new Forum();
            forum.setAuthor(forumDTO.getAuthor());
            forum.setTitle(forumDTO.getTitle());
            forum.setDescription(forumDTO.getDescription());
            forum.setCategory(forumDTO.getCategory());
            forum.setDateTime(LocalDateTime.now());
            forum.setViewCount(0);
            forum.setLikeCount(0);
            forum.setCommentsCount(0);
            forum.setQuestion(forumDTO.getQuestion());
            
            em.getTransaction().begin();
            em.persist(forum);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao criar fórum: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void addComment(String forumTitle, ForumComentarioDTO commentDTO) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            TypedQuery<Forum> query = em.createQuery(
                "SELECT f FROM Forum f WHERE f.title = :title", Forum.class);
            query.setParameter("title", forumTitle);
            Forum forum = query.getSingleResult();
            
            ForumComment comment = new ForumComment();
            comment.setForum(forum);
            comment.setUserName(commentDTO.getUsuario());
            comment.setCommentText(commentDTO.getComentario());
            comment.setCommentDate(LocalDateTime.now());
            
            forum.setCommentsCount(forum.getCommentsCount() + 1);
            
            em.persist(comment);
            em.merge(forum);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar comentário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<ForumDTO> getAllForums() {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<Forum> query = em.createQuery(
                "SELECT f FROM Forum f ORDER BY f.dateTime DESC", 
                Forum.class);
            
            return query.getResultList().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    private ForumDTO convertToDTO(Forum forum) {
        return new ForumDTO(
            forum.getAuthor(),
            forum.getTitle(),
            forum.getDescription(),
            forum.getCategory(),
            forum.getDateTime().toString(),
            forum.getViewCount(),
            forum.getLikeCount(),
            forum.getCommentsCount(),
            forum.getQuestion()
        );
    }

    public void incrementViewCount(String forumTitle) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            TypedQuery<Forum> query = em.createQuery(
                "SELECT f FROM Forum f WHERE f.title = :title", Forum.class);
            query.setParameter("title", forumTitle);
            Forum forum = query.getSingleResult();
            
            forum.setViewCount(forum.getViewCount() + 1);
            
            em.merge(forum);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao incrementar visualizações: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<ForumDTO> getForumsByAuthor(String author) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<Forum> query = em.createQuery(
                "SELECT f FROM Forum f WHERE f.author = :author ORDER BY f.dateTime DESC", 
                Forum.class);
            query.setParameter("author", author);
            
            return query.getResultList().stream()
                .map(this::convertToDTO)
                .toList();
        } finally {
            em.close();
        }
    }

    public Forum getForumByTitle(String title) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<Forum> query = em.createQuery(
                "SELECT f FROM Forum f WHERE f.title = :title", 
                Forum.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<ForumComment> getForumComments(String title) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            TypedQuery<ForumComment> query = em.createQuery(
                "SELECT c FROM ForumComment c JOIN c.forum f WHERE f.title = :title ORDER BY c.commentDate DESC", 
                ForumComment.class);
            query.setParameter("title", title);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<ForumComentarioDTO> getComments(String forumTitle) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Forum forum = em.createQuery(
                "SELECT f FROM Forum f WHERE f.title = :title", Forum.class)
                .setParameter("title", forumTitle)
                .getSingleResult();
            
            return forum.getComments().stream()
                .map(comment -> new ForumComentarioDTO(
                    comment.getCommentDate().toString(),
                    comment.getUserName(),
                    comment.getCommentText()))
                .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
} 