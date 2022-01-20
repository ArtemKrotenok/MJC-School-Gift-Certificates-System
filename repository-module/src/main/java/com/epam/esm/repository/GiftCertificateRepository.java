package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends GenericRepository<GiftCertificate> {

    List<GiftCertificate> getAllByPageSorted(int startPosition, int itemsByPage);

    List<GiftCertificate> findByTag(String tag);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByDescription(String description);
}
