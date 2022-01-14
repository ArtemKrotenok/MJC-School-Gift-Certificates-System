package com.epam.esm.service;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.util.GiftCertificateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GiftCertificateUtilTest {

    @Test
    void convert_DTO_to_entity() {
        GiftCertificateDTO validGiftCertificateDTO = TestDataUtil.getValidGiftCertificateDTO();
        GiftCertificate validGiftCertificate = TestDataUtil.getValidGiftCertificate();
        GiftCertificate covertGiftCertificate = GiftCertificateUtil.convert(validGiftCertificateDTO);
        assertThat(covertGiftCertificate).isEqualTo(validGiftCertificate);
    }

    @Test
    void convert_DTO_NULL_to_entity() {
        GiftCertificateDTO nullGiftCertificateDTO = null;
        GiftCertificate covertGiftCertificateDTO = GiftCertificateUtil.convert(nullGiftCertificateDTO);
        assertThat(covertGiftCertificateDTO).isNull();
    }

    @Test
    void convert_entity_to_DTO() {
        GiftCertificate validGiftCertificate = TestDataUtil.getValidGiftCertificate();
        GiftCertificateDTO validGiftCertificateDTO = TestDataUtil.getValidGiftCertificateDTO();
        GiftCertificateDTO covertGiftCertificateDTO = GiftCertificateUtil.convert(validGiftCertificate);
        assertThat(covertGiftCertificateDTO).isEqualTo(validGiftCertificateDTO);
    }

    @Test
    void convert_entity_NULL_to_DTO() {
        GiftCertificate nullGiftCertificate = null;
        GiftCertificateDTO covertGiftCertificateDTO = GiftCertificateUtil.convert(nullGiftCertificate);
        assertThat(covertGiftCertificateDTO).isNull();
    }
}