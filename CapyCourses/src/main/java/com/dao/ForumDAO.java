package com.dao;

import com.util.JPAUtil;
import javax.persistence.EntityManager;

public class ForumDAO {

  public void updateLikes(String title, int likes) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.createQuery("UPDATE Forum f SET f.likeCount = :likes WHERE f.title = :title")
          .setParameter("likes", likes)
          .setParameter("title", title)
          .executeUpdate();
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  public void updateComments(String title, int comments) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
      em.getTransaction().begin();
      em.createQuery("UPDATE Forum f SET f.commentsCount = :comments WHERE f.title = :title")
          .setParameter("comments", comments)
          .setParameter("title", title)
          .executeUpdate();
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }
}
