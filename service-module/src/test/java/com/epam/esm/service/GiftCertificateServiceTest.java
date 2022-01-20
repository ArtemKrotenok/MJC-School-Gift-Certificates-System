package com.epam.esm.service;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.imp.GiftCertificateServiceImpl;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.SuccessResponseDTO;
import com.epam.esm.service.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.esm.repository.impl.GiftCertificateRepositoryImpl.ONE_RESULT;
import static com.epam.esm.service.TestServiceDataUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void creat_validGiftCertificate_SuccessResponseDTO() throws GiftCertificateServiceException {
        GiftCertificateDTO giftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.add(any(GiftCertificate.class))).thenReturn(1L);
        SuccessResponseDTO successResponseDTO = giftCertificateService.create(giftCertificateDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.CREATE.getCode());
    }

    @Test
    void creat_invalidGiftCertificate_GiftCertificateServiceException() {
        GiftCertificateDTO giftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        giftCertificateDTO.setName(null);
        Exception exception = assertThrows(Exception.class, () -> {
            giftCertificateService.create(giftCertificateDTO);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_VALID_INPUT_DATA.getCode());
    }

    @Test
    void findById_validId_GiftCertificateDTO() throws GiftCertificateServiceException {
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(TestServiceDataUtil.getValidGiftCertificate());
        GiftCertificateDTO finedGiftCertificateDTO = giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID);
        assertEquals(finedGiftCertificateDTO, TestServiceDataUtil.getValidGiftCertificateDTO());
    }

    @Test
    void findById_invalidId_GiftCertificateServiceException() {
        Exception exception = assertThrows(Exception.class, () -> {
            giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void deleteById_validId_SuccessResponseDTO() throws GiftCertificateServiceException {
        GiftCertificate validGiftCertificate = TestServiceDataUtil.getValidGiftCertificate();
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.delete(validGiftCertificate)).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = giftCertificateService.deleteById(GIFT_CERTIFICATE_TEST_ID);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.DELETE.getCode());
    }

    @Test
    void deleteById_invalidId_GiftCertificateServiceException() {
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST)).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            giftCertificateService.deleteById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void update_validGiftCertificateDTO_SuccessResponseDTO() throws GiftCertificateServiceException {
        GiftCertificate validGiftCertificate = TestServiceDataUtil.getValidGiftCertificate();
        GiftCertificateDTO validGiftCertificateDTO = TestServiceDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.findById(validGiftCertificate.getId())).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.update(any(GiftCertificate.class))).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = giftCertificateService.update(validGiftCertificateDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.UPDATE.getCode());
    }

    @Test
    void getAllByPageSorted_returnSortedCertificateDTOs() throws GiftCertificateServiceException {
        when(giftCertificateRepository.getAllByPageSorted(0, PaginationUtil.ITEMS_BY_PAGE)).
                thenReturn(TestServiceDataUtil.getValidGiftCertificateList(TestServiceDataUtil.COUNT_TEST_GIFT_CERTIFICATE_LIST));
        List<GiftCertificateDTO> finedGiftCertificateDTOList = giftCertificateService.getAllByPageSorted(1);
        assertEquals(finedGiftCertificateDTOList, TestServiceDataUtil.getValidGiftCertificateDTOList(COUNT_TEST_GIFT_CERTIFICATE_LIST));
    }
}