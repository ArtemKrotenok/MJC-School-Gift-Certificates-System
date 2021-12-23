package com.epam.esm.repository.impl;

import com.epam.esm.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}