package com.epam.esm.service.imp;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.util.GiftCertificateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepository giftCertificateRepository;

    @Override
    public List<GiftCertificateDTO> getCertificateByTag(String tag) {
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findByTag(tag);

        return giftCertificateList.stream()
                .map(GiftCertificateUtil::covert)
                .collect(Collectors.toList());
    }
}