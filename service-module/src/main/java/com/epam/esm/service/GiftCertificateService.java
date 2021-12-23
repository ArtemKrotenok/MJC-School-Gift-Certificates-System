package com.epam.esm.service;

import com.epam.esm.service.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDTO> getCertificateByTag(String tag);

}
