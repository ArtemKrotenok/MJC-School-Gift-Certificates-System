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
        GiftCertificate giftCertificate = jdbcTemplate.query("SELECT * FROM gift_certificate WHERE id=?",
                        new BeanPropertyRowMapper<>(GiftCertificate.class),
                        id)
                .stream().findAny().orElse(null);
        if (giftCertificate != null) {
            giftCertificate.setTagList(getTagListForCertificate(giftCertificate));
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
            giftCertificateTagRepository.addByTagList(insertedId, giftCertificate.getTagList());
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
            giftCertificateTagRepository.addByTagList(giftCertificate.getId(), giftCertificate.getTagList());
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
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query("SELECT DISTINCT " +
                        "gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date " +
                        "FROM gift_certificate AS gc " +
                        "JOIN gift_certificate_tag AS gct ON gc.id = gct.id_gift_certificate " +
                        "JOIN tag t ON gct.id_tag = t.id WHERE t.name = ?",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                tag);
        if (giftCertificateList.size() > 0) {
            giftCertificateList.forEach(element -> element.setTagList(getTagListForCertificate(element)));
        }
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query("SELECT * FROM gift_certificate WHERE name LIKE CONCAT( '%',?,'%')",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                name);
        if (giftCertificateList.size() > 0) {
            giftCertificateList.forEach(element -> element.setTagList(getTagListForCertificate(element)));
        }
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query("SELECT * FROM gift_certificate WHERE description LIKE CONCAT( '%',?,'%')",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                description);
        if (giftCertificateList.size() > 0) {
            giftCertificateList.forEach(element -> element.setTagList(getTagListForCertificate(element)));
        }
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificateByPageSorted(int startPosition, int itemsByPage) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query("SELECT * FROM gift_certificate ORDER BY name ASC OFFSET ? LIMIT ?",
                new BeanPropertyRowMapper<>(GiftCertificate.class),
                startPosition,
                itemsByPage);
        if (giftCertificateList.size() > 0) {
            giftCertificateList.forEach(element -> element.setTagList(getTagListForCertificate(element)));
        }
        return giftCertificateList;
    }

    private List<Tag> getTagListForCertificate(GiftCertificate giftCertificate) {
        return giftCertificateTagRepository.findByGiftCertificateId(giftCertificate.getId());
       /* return jdbcTemplate.query("SELECT DISTINCT " +
                        "t.id, t.name " +
                        "FROM tag AS t " +
                        "JOIN gift_certificate_tag gct ON t.id = gct.id_tag " +
                        "JOIN gift_certificate gc ON gc.id = gct.id_gift_certificate " +
                        "WHERE gc.id = ?",
                new BeanPropertyRowMapper<>(Tag.class),
                giftCertificate.getId());*/
    }
}
