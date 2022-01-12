package com.epam.esm.service;

import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.SuccessResponseDTO;
import com.epam.esm.service.model.TagDTO;

import java.util.List;

public interface TagService {

    SuccessResponseDTO creat(TagDTO tagDTO) throws GiftCertificateServiceException;

    List<TagDTO> getAllTagsByPageSorted(Integer page) throws GiftCertificateServiceException;

    List<TagDTO> getAllTagsSorted() throws GiftCertificateServiceException;

    TagDTO findById(Long id) throws GiftCertificateServiceException;

    SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException;
}