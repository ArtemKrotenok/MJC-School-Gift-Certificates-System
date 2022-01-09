package com.epam.esm.service;

import com.epam.esm.service.model.GiftCertificateDTO;

public interface GiftCertificateService {

    String getCertificateByTag(String tag);

    String getAllGiftCertificateByPageSorted(Integer page);

    String add(GiftCertificateDTO giftCertificateDTO);

    String deleteById(Long id);

    String findById(Long id);

    String update(GiftCertificateDTO giftCertificateDTO);

    String searchGiftCertificate(String tag, String name, String description);
}
