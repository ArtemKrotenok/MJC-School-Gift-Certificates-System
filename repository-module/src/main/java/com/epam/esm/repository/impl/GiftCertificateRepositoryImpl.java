package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@AllArgsConstructor
public class GiftCertificateRepositoryImpl extends GenericRepositoryImpl<GiftCertificate> implements GiftCertificateRepository {

    public static final int ONE_RESULT = 1;
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateTagRepository giftCertificateTagRepository;

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate", new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = getOneResult(jdbcTemplate.query("SELECT * FROM gift_certificate WHERE id=?",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                id));
        if (giftCertificate != null) {
            giftCertificate.setTags(getTagsByGiftCertificateId(giftCertificate.getId()));
        }
        return giftCertificate;
    }

    @Override
    public Long add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO gift_certificate " +
                "(name, description, price, duration, create_date, last_update_date) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setLong(4, giftCertificate.getDuration());
            ps.setTimestamp(5, giftCertificate.getCreateDate());
            ps.setTimestamp(6, giftCertificate.getLastUpdateDate());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeys() != null) {
            long insertedId = (long) keyHolder.getKeys().get("id");
            giftCertificateTagRepository.add(insertedId, giftCertificate.getTags());
            return insertedId;
        }
        return null;
    }

    @Override
    public int update(GiftCertificate giftCertificate) {
        int effectRows = jdbcTemplate.update("UPDATE gift_certificate " +
                        "SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? " +
                        "WHERE id=?",
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId());
        if (effectRows == ONE_RESULT) {
            giftCertificateTagRepository.deleteByGiftCertificateId(giftCertificate.getId());
            giftCertificateTagRepository.add(giftCertificate.getId(), giftCertificate.getTags());
        }
        return effectRows;
    }

    @Override
    public int delete(GiftCertificate giftCertificate) {
        giftCertificateTagRepository.deleteByGiftCertificateId(giftCertificate.getId());
        return jdbcTemplate.update("DELETE FROM gift_certificate WHERE id=?", giftCertificate.getId());
    }

    @Override
    public List<GiftCertificate> findByTag(String tag) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query("SELECT DISTINCT " +
                        "gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date " +
                        "FROM gift_certificate AS gc " +
                        "JOIN gift_certificate_tag AS gct ON gc.id = gct.id_gift_certificate " +
                        "JOIN tag t ON gct.id_tag = t.id WHERE t.name = ? ORDER BY name ASC",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                tag);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(element -> element.setTags(getTagsByGiftCertificateId(element.getId())));
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate WHERE name LIKE CONCAT( '%',?,'%') ORDER BY name ASC",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                name);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(element -> element.setTags(getTagsByGiftCertificateId(element.getId())));
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate WHERE description LIKE CONCAT( '%',?,'%') ORDER BY name ASC",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                description);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(element -> element.setTags(getTagsByGiftCertificateId(element.getId())));
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> search(int startPosition, int itemsByPage, String tag, String name, String description) {
        if (tag == null) {
            tag = "%";
        }
        if (name == null) {
            name = "%";
        } else {
            name = name.trim();
            name = "%" + name + "%";
        }
        if (description == null) {
            description = "%";
        } else {
            description = description.trim();
            description = "%" + description + "%";
        }
        List<GiftCertificate> giftCertificates = jdbcTemplate.query("SELECT DISTINCT " +
                        "gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date " +
                        "FROM gift_certificate AS gc " +
                        "JOIN gift_certificate_tag AS gct ON gc.id = gct.id_gift_certificate " +
                        "JOIN tag t ON gct.id_tag = t.id " +
                        "WHERE t.name LIKE ? " +
                        "AND gc.name LIKE ? " +
                        "AND gc.description LIKE ? " +
                        "ORDER BY name ASC LIMIT ? OFFSET ?",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                tag, name, description, itemsByPage, startPosition);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(element -> element.setTags(getTagsByGiftCertificateId(element.getId())));
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> getAllByPageSorted(int startPosition, int itemsByPage) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate ORDER BY name ASC LIMIT ? OFFSET ?",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                itemsByPage,
                startPosition);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(element -> element.setTags(getTagsByGiftCertificateId(element.getId())));
        }
        return giftCertificates;
    }

    private List<Tag> getTagsByGiftCertificateId(Long id) {
        return giftCertificateTagRepository.findByGiftCertificateId(id);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM gift_certificate", Long.class);
    }

}
