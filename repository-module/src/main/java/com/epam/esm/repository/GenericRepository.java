package com.epam.esm.repository;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> {

    Connection getConnection() throws SQLException;

    List<T> findAll();

    T findById(Long id);

    void add(T object);

    void update(T object);

    void delete(T object);

}
