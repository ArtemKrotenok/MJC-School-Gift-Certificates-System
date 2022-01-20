package com.epam.esm.service;

import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.SuccessResponseDTO;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDTO> getAllByPageSorted(Integer page) throws GiftCertificateServiceException;

    SuccessResponseDTO create(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException;

    SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException;

    GiftCertificateDTO findById(Long id) throws GiftCertificateServiceException;

    SuccessResponseDTO update(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException;

    List<GiftCertificateDTO> search(String tag, String name, String description) throws GiftCertificateServiceException;
}