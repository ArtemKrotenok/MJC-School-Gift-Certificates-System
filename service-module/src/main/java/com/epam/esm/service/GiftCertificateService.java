package com.epam.esm.service;

import com.epam.esm.service.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {

    void create(GiftCertificateDTO giftCertificateDTO);

    void deleteById(Long id);

    GiftCertificateDTO findById(Long id);

    void update(GiftCertificateDTO giftCertificateDTO);

    List<GiftCertificateDTO> search(Integer page, String tag, String name, String description);
}