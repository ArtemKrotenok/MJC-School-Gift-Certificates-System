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
import static com.epam.esm.service.TestDataUtil.*;
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
    void creat_valid_giftCertificate() throws GiftCertificateServiceException {
        GiftCertificateDTO giftCertificateDTO = TestDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.add(any(GiftCertificate.class))).thenReturn(1L);
        SuccessResponseDTO successResponseDTO = giftCertificateService.create(giftCertificateDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.CREATE.getCode());
    }

    @Test
    void creat_not_valid_giftCertificate() {
        GiftCertificateDTO giftCertificateDTO = TestDataUtil.getValidGiftCertificateDTO();
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
    void find_giftCertificate_by_id() throws GiftCertificateServiceException {
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(TestDataUtil.getValidGiftCertificate());
        GiftCertificateDTO finedGiftCertificateDTO = giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID);
        assertEquals(finedGiftCertificateDTO, TestDataUtil.getValidGiftCertificateDTO());
    }

    @Test
    void find_giftCertificate_by_not_valid_id() {
        Exception exception = assertThrows(Exception.class, () -> {
            giftCertificateService.findById(GIFT_CERTIFICATE_TEST_ID_NOT_EXIST);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void delete_giftCertificate_by_id() throws GiftCertificateServiceException {
        GiftCertificate validGiftCertificate = TestDataUtil.getValidGiftCertificate();
        when(giftCertificateRepository.findById(GIFT_CERTIFICATE_TEST_ID)).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.delete(validGiftCertificate)).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = giftCertificateService.deleteById(GIFT_CERTIFICATE_TEST_ID);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.DELETE.getCode());
    }

    @Test
    void delete_giftCertificate_by_not_valid_id() {
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
    void update_gift_certificate() throws GiftCertificateServiceException {
        GiftCertificate validGiftCertificate = TestDataUtil.getValidGiftCertificate();
        GiftCertificateDTO validGiftCertificateDTO = TestDataUtil.getValidGiftCertificateDTO();
        when(giftCertificateRepository.findById(validGiftCertificate.getId())).thenReturn(validGiftCertificate);
        when(giftCertificateRepository.update(any(GiftCertificate.class))).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = giftCertificateService.update(validGiftCertificateDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.UPDATE.getCode());
    }

    @Test
    void get_all_gift_certificate_by_page_sorted() throws GiftCertificateServiceException {
        when(giftCertificateRepository.getAllGiftCertificateByPageSorted(0, PaginationUtil.ITEMS_BY_PAGE)).
                thenReturn(TestDataUtil.getValidGiftCertificateList(TestDataUtil.COUNT_TEST_GIFT_CERTIFICATE_LIST));
        List<GiftCertificateDTO> finedGiftCertificateDTOList = giftCertificateService.getAllGiftCertificateByPageSorted(1);
        assertEquals(finedGiftCertificateDTOList, TestDataUtil.getValidGiftCertificateDTOList(COUNT_TEST_GIFT_CERTIFICATE_LIST));
    }
}