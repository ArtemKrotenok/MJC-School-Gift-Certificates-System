package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends GenericRepository<GiftCertificate> {

    List<GiftCertificate> findByTag(String tag);
}
