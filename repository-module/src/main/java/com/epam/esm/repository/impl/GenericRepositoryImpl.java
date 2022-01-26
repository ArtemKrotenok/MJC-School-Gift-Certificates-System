package com.epam.esm.repository.impl;

import com.epam.esm.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {

    private static final int INDEX_FIRST_ELEMENT = 0;

    @Autowired
    private DataSource dataSource;

    public T getOneResult(List<T> results) {
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(INDEX_FIRST_ELEMENT);
        }
    }
}
