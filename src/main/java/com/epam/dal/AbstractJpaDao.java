package com.epam.dal;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Alexey on 12.05.2016.
 */
public abstract class AbstractJpaDao<T extends Serializable> {

    private Class<T> entityClass;

    @PersistenceContext(unitName = "defaultPU")
    EntityManager entityManager;

    public AbstractJpaDao() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findOne(String id) {
        return entityManager.find(entityClass, id);
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(String entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }
}

