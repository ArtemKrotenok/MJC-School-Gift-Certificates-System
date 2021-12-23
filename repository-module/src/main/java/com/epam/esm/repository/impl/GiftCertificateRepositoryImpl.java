package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.GiftCertificateTag;
import com.epam.esm.repository.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GiftCertificateRepositoryImpl extends GenericRepositoryImpl<GiftCertificate> implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateTagRepository giftCertificateTagRepository;

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate", new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public GiftCertificate findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM gift_certificate WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(GiftCertificate.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO gift_certificate " +
                        "(name, description, price, duration, create_date, last_update_date) " +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), keyHolder);
        if (keyHolder.getKey() != null) {
            updateTagListForCertificateInDB(keyHolder.getKey().longValue(), giftCertificate.getTagList());
        }
    }

    private void updateTagListForCertificateInDB(Long idGiftCertificate, List<Tag> tagList) {
        tagList.forEach(element -> giftCertificateTagRepository.add(new GiftCertificateTag(idGiftCertificate, element.getId())));
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update("UPDATE gift_certificate " +
                        "SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? " +
                        "WHERE id=?",
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId());
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        jdbcTemplate.update("DELETE FROM gift_certificate WHERE id=?", giftCertificate.getId());
    }

    @Override
    public List<GiftCertificate> findByTag(String tag) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query("SELECT DISTINCT " +
                        "gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date " +
                        "FROM gift_certificate AS gc " +
                        "JOIN gift_certificate_tag AS gct ON gc.id = gct.id_gift_certificate " +
                        "JOIN tag t ON gct.id_tag = t.id WHERE t.name = ?",
                new Object[]{tag}, new BeanPropertyRowMapper<>(GiftCertificate.class));
        if (giftCertificateList.size() > 0) {
            giftCertificateList.forEach(element -> element.setTagList(getTagListForCertificate(element)));
        }
        return giftCertificateList;
    }

    private List<Tag> getTagListForCertificate(GiftCertificate giftCertificate) {
        return jdbcTemplate.query("SELECT DISTINCT " +
                        "t.id, t.name " +
                        "FROM tag AS t " +
                        "JOIN gift_certificate_tag gct ON t.id = gct.id_tag " +
                        "JOIN gift_certificate gc ON gc.id = gct.id_gift_certificate " +
                        "WHERE gc.id = ?",
                new Object[]{giftCertificate.getId()}, new BeanPropertyRowMapper<>(Tag.class));
    }
}
