package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificateTag;
import com.epam.esm.repository.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class GiftCertificateTagRepositoryImpl extends GenericRepositoryImpl<GiftCertificateTag> implements GiftCertificateTagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TagRepository tagRepository;

    @Override
    public List<GiftCertificateTag> findAll() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate_tag", new BeanPropertyRowMapper<>(GiftCertificateTag.class));
    }

    @Override
    public GiftCertificateTag findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM gift_certificate_tag WHERE id=?",
                        new BeanPropertyRowMapper<>(GiftCertificateTag.class),
                        id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Long add(GiftCertificateTag giftCertificateTag) {
        return (long) jdbcTemplate.update("INSERT INTO gift_certificate_tag (id_gift_certificate, id_tag) VALUES(?,?)",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }

    @Override
    public int update(GiftCertificateTag giftCertificateTag) {
        return jdbcTemplate.update("UPDATE gift_certificate_tag SET id_gift_certificate=?, id_tag=? WHERE id=?",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }

    @Override
    public int delete(GiftCertificateTag giftCertificateTag) {
        return jdbcTemplate.update("DELETE FROM gift_certificate_tag WHERE id_gift_certificate=? AND id_tag=?",
                giftCertificateTag.getIdGiftCertificate(),
                giftCertificateTag.getIdTag());
    }

    @Override
    public void add(Long idGiftCertificate, List<Tag> tagList) {
        List<Tag> saveTagList = tagRepository.updateTags(tagList);
        saveTagList.forEach(element ->
                add(GiftCertificateTag.builder()
                        .idGiftCertificate(idGiftCertificate)
                        .idTag(element.getId())
                        .build()
                )
        );
    }

    @Override
    public int deleteByGiftCertificateId(Long id) {
        return jdbcTemplate.update("DELETE FROM gift_certificate_tag WHERE id_gift_certificate=?",
                id);
    }

    @Override
    public List<Tag> findByGiftCertificateId(Long idGiftCertificate) {
        List<Long> idTagList = jdbcTemplate.queryForList(
                "SELECT id_tag FROM gift_certificate_tag WHERE id_gift_certificate=?",
                Long.class,
                idGiftCertificate);
        List<Tag> resultTagList = new ArrayList<>();
        for (Long idTag : idTagList) {
            resultTagList.add(tagRepository.findById(idTag));
        }
        return resultTagList;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM gift_certificate_tag", Long.class);
    }
}