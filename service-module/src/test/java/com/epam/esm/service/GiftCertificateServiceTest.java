package com.epam.esm.service;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.imp.GiftCertificateServiceImpl;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.esm.repository.impl.GiftCertificateRepositoryImpl.ONE_RESULT;
import static com.epam.esm.service.TestServiceDataUtil.GIFT_CERTIFICATE_TEST_ID;
import static com.epam.esm.service.TestServiceDataUtil.GIFT_CERTIFICATE_TEST_ID_NOT_EXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    GiftCertificateRepository giftCertificateRepository;
    GiftCertificateService giftCertificateService;

    @BeforeEach
    public void setup() {
        this.giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository);
    }

    @Test
    void create_givenValidGiftCertificate() {
        GiftCertificateDTO giftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.add(any(GiftCertificate.class))).thenReturn(1L);
        giftCertificateService.create(giftCertificateDTO);
    }

    @Test
    void create_givenInvalidGiftCertificate_returnsGiftCertificateServiceException() {
        GiftCertificateDTO giftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        giftCertificateDTO.setName(null);
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            giftCertificateService.create(giftCertificateDTO);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_VALID_INPUT_DATA.getCode());
    }

    @Test
    void findById_givenValidId_returnsGiftCertificateDTO() {
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(TestServiceDataUtil.getValidGiftCertificate());
        GiftCertificateDTO finedGiftCertificateDTO = giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID);
        assertEquals(finedGiftCertificateDTO, TestServiceDataUtil.getValidGiftCertificateDTO());
    }

    @Test
    void findById_givenInvalidId_returnsGiftCertificateServiceException() {
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void deleteById_givenValidId() {
        GiftCertificate validGiftCertificate = TestServiceDataUtil.getValidGiftCertificate();
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.delete(validGiftCertificate)).thenReturn(ONE_RESULT);
        giftCertificateService.deleteById(GIFT_CERTIFICATE_TEST_ID);
    }

    @Test
    void deleteById_givenInvalidId_returnsGiftCertificateServiceException() {
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST)).thenReturn(null);
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            giftCertificateService.deleteById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void update_givenValidGiftCertificateDTO_returnsSuccessResponseDTO() {
        GiftCertificate validGiftCertificate = TestServiceDataUtil.getValidGiftCertificate();
        GiftCertificateDTO validGiftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.findById(validGiftCertificate.getId())).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.update(any(GiftCertificate.class))).thenReturn(ONE_RESULT);
        giftCertificateService.update(validGiftCertificateDTO);
    }
}