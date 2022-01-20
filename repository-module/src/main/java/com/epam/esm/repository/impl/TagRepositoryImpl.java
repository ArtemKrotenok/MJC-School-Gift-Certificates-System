package com.epam.esm.repository.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
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
    public Tag findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE name=?",
                        new BeanPropertyRowMapper<>(Tag.class),
                        name)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Tag> getAllByPageSorted(int startPosition, int itemsByPage) {
        return jdbcTemplate.query("SELECT * FROM tag ORDER BY name ASC LIMIT ? OFFSET ?",
                new BeanPropertyRowMapper<>(Tag.class),
                itemsByPage,
                startPosition);
    }

    @Override
    public List<Tag> getAllSorted() {
        return jdbcTemplate.query("SELECT * FROM tag ORDER BY name ASC",
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Tag> updateTags(List<Tag> tagList) {
        List<Tag> resultList = new ArrayList<>();
        for (Tag tag : tagList) {
            Tag tagBD = findByName(tag.getName());
            if (tagBD == null) {
                tagBD = Tag.builder()
                        .id(add(tag))
                        .name(tag.getName())
                        .build();
            }
            resultList.add(tagBD);
        }
        return resultList;
    }

    @Override
    public Tag findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?",
                        new BeanPropertyRowMapper<>(Tag.class),
                        id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Long add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO tag (name) VALUES(?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeys() != null) {
            return (long) keyHolder.getKeys().get("id");
        }
        return null;
    }

    @Override
    public int update(Tag Tag) {
        return jdbcTemplate.update("UPDATE tag SET name=? WHERE id=?", Tag.getName(), Tag.getId());
    }

    @Override
    public int delete(Tag Tag) {
        return jdbcTemplate.update("DELETE FROM tag WHERE id=?", Tag.getId());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tag", Long.class);
    }
}