package com.model.factory;

import javax.persistence.*;

public class DatabaseJPA {
  private static DatabaseJPA instance;
  private EntityManagerFactory factory;

  private DatabaseJPA() {
    this.factory = Persistence.createEntityManagerFactory("capycourses");
  }

  public static DatabaseJPA getInstance() {
    if (instance == null) instance = new DatabaseJPA();
    return instance;
  }

  public EntityManager getEntityManager() {
    return factory.createEntityManager();
  }

  public void closeFactory() {
    if (factory != null && factory.isOpen()) factory.close();
  }
}
