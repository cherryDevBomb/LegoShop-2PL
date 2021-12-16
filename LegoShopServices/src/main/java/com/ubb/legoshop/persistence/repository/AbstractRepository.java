package com.ubb.legoshop.persistence.repository;

import java.util.List;

public interface AbstractRepository<T> {

    T getById(Long id);

    List<T> getAll();

    T add(T entity);

    void delete(T entity);
}
