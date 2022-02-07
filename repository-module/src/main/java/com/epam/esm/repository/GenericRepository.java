package com.epam.esm.repository;

import java.util.List;

public interface GenericRepository<T> {

    List<T> findAll();

    T findById(Long id);

    Long add(T object);

    int update(T object);

    int delete(T object);

    long count();
}
