package com.epam.esm.repository;

import com.epam.esm.app.config.AppConfig;
import com.epam.esm.app.config.AppConfigTest;
import com.epam.esm.repository.model.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, AppConfigTest.class})
@ActiveProfiles("test")
class GiftCertificateRepositoryTest {

    public static final String TEST_GIFT_CERTIFICATE_NAME_EXIST_IN_DB = "race";
    public static final String TEST_GIFT_CERTIFICATE_NAME_NO_EXIST_IN_DB = "extreme";
    public static final long TEST_GIFT_CERTIFICATE_ID_EXIST_IN_DB = 3L;
    public static final long TEST_GIFT_CERTIFICATE_ID_FOR_DELETE = 2L;
    public static final long TEST_GIFT_CERTIFICATE_ID_NO_EXIST_IN_DB = 30L;
    public static final int START_POSITION = 0;
    public static final int ITEMS_BY_PAGE = 3;
    public static final String NEW_GIFT_CERTIFICATE_NAME = "new_name";
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void findAll_returnsGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        assertEquals(giftCertificates.size(), giftCertificateRepository.count());
    }

    @Test
    void add_givenValidGiftCertificate_returnsIdNewRecord() {
        GiftCertificate giftCertificate = TestRepositoryDataUtil.getValidGiftCertificate();
        Long idNewRecord = giftCertificateRepository.add(giftCertificate);
        assertThat(idNewRecord).isNotNull();
        assertTrue(idNewRecord > 0);
    }

    @Test
    void add_givenValidGiftCertificate_returnsCountGiftCertificateInDBIncrement() {
        long giftCertificatesBeforeAdd = giftCertificateRepository.count();
        GiftCertificate giftCertificate = TestRepositoryDataUtil.getValidGiftCertificate();
        giftCertificateRepository.add(giftCertificate);
        long giftCertificatesAfterAdd = giftCertificateRepository.count();
        assertEquals(giftCertificatesAfterAdd - giftCertificatesBeforeAdd, 1);
    }

    @Test
    void getGiftCertificateByName_givenValidName_returnsValidGiftCertificate() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByName(TEST_GIFT_CERTIFICATE_NAME_EXIST_IN_DB);
        assertThat(giftCertificates).isNotNull();
        giftCertificates.forEach(
                giftCertificate -> assertEquals(giftCertificate.getName(), TEST_GIFT_CERTIFICATE_NAME_EXIST_IN_DB));

    }

    @Test
    void getGiftCertificateByName_givenInvalidName_returnsNull() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByName(TEST_GIFT_CERTIFICATE_NAME_NO_EXIST_IN_DB);
        assertTrue(giftCertificates.isEmpty());
    }

    @Test
    void getAllByPageSorted_returnsSortedGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.getAllByPageSorted(START_POSITION, ITEMS_BY_PAGE);
        assertFalse(giftCertificates.isEmpty());
        assertTrue(isSorted(giftCertificates));
        assertEquals(giftCertificates.size(), ITEMS_BY_PAGE);
    }

    @Test
    void findById_givenValidId_returnsGiftCertificate() {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_EXIST_IN_DB);
        assertThat(giftCertificate).isNotNull();
        assertEquals(giftCertificate.getId(), TEST_GIFT_CERTIFICATE_ID_EXIST_IN_DB);
    }

    @Test
    void findById_givenInvalidId_returnsNull() {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_NO_EXIST_IN_DB);
        assertThat(giftCertificate).isNull();
    }

    @Test
    void delete_givenValidId_returnsRecordEffectOne() {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_FOR_DELETE);
        assertThat(giftCertificate).isNotNull();
        int effectRows = giftCertificateRepository.delete(giftCertificate);
        assertEquals(effectRows, 1);
        GiftCertificate deletedGiftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_FOR_DELETE);
        assertThat(deletedGiftCertificate).isNull();
    }

    @Test
    void update_givenValidGiftCertificate_returnsRecordEffectOne() {
        GiftCertificate dbGiftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_EXIST_IN_DB);
        dbGiftCertificate.setName(NEW_GIFT_CERTIFICATE_NAME);
        int effectRows = giftCertificateRepository.update(dbGiftCertificate);
        assertEquals(effectRows, 1);
        GiftCertificate updateGiftCertificate = giftCertificateRepository.findById(TEST_GIFT_CERTIFICATE_ID_EXIST_IN_DB);
        assertEquals(updateGiftCertificate.getName(), NEW_GIFT_CERTIFICATE_NAME);
    }

    private boolean isSorted(List<GiftCertificate> giftCertificates) {
        GiftCertificate preGiftCertificate = giftCertificates.get(0);
        boolean isSorted = true;
        for (GiftCertificate giftCertificate : giftCertificates) {
            if (preGiftCertificate.getName().compareTo(giftCertificate.getName()) > 0) {
                isSorted = false;
            }
            preGiftCertificate = giftCertificate;
        }
        return isSorted;
    }
}