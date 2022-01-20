package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificateTag;
import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface GiftCertificateTagRepository extends GenericRepository<GiftCertificateTag> {

    void add(Long idGiftCertificate, List<Tag> tagList);

    int deleteByGiftCertificateId(Long id);

    List<Tag> findByGiftCertificateId(Long id);
}
