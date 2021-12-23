package com.epam.esm.repository.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TagRepositoryImpl extends GenericRepositoryImpl<Tag> implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void add(Tag Tag) {
        jdbcTemplate.update("INSERT INTO tag (name) VALUES(?)", Tag.getName());
    }

    @Override
    public void update(Tag Tag) {
        jdbcTemplate.update("UPDATE tag SET name=? WHERE id=?", Tag.getName(), Tag.getId());
    }

    @Override
    public void delete(Tag Tag) {
        jdbcTemplate.update("DELETE FROM tag WHERE id=?", Tag.getId());
    }
}