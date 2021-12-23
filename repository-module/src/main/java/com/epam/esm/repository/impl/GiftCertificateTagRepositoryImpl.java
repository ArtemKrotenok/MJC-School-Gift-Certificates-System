package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.model.GiftCertificateTag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GiftCertificateTagRepositoryImpl extends GenericRepositoryImpl<GiftCertificateTag> implements GiftCertificateTagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificateTag> findAll() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate_tag", new BeanPropertyRowMapper<>(GiftCertificateTag.class));
    }

    @Override
    public GiftCertificateTag findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM gift_certificate_tag WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(GiftCertificateTag.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void add(GiftCertificateTag giftCertificateTag) {
        jdbcTemplate.update("INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag) VALUES(?)",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }

    @Override
    public void update(GiftCertificateTag giftCertificateTag) {
        jdbcTemplate.update("UPDATE gift_certificate_tag SET id_gift_certificate=?, id_tag=? WHERE id=?",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }

    @Override
    public void delete(GiftCertificateTag giftCertificateTag) {
        jdbcTemplate.update("DELETE FROM gift_certificate_tag WHERE id_gift_certificate=? AND id_tag=?",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }
}