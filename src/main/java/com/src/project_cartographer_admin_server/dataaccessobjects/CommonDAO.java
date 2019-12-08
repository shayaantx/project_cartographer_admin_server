package com.src.project_cartographer_admin_server.dataaccessobjects;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public abstract class CommonDAO<T> {
  @PersistenceContext
  protected EntityManager entityManager;

  public T loadEntity(Object primaryKey) {
    return entityManager.getReference(getClazz(), primaryKey);
  }

  public abstract Class<T> getClazz();
}
