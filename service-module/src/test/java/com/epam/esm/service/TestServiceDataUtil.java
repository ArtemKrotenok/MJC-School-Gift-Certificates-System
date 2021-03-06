package com.epam.esm.service;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.TagDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestServiceDataUtil {

    public static final long TAG_TEST_ID = 5L;
    public static final long TAG_TEST_ID_NOT_EXIST = 1000L;
    public static final long GIFT_CERTIFICATE_TEST_ID_NOT_EXIST = 1000L;
    public static final String TAG_TEST_NAME = "tag_name";
    public static final long GIFT_CERTIFICATE_TEST_ID = 10L;
    public static final String GIFT_CERTIFICATE_TEST_NAME = "certificate_name";
    public static final String GIFT_CERTIFICATE_TEST_DESCRIPTION = "description_certificate";
    public static final long GIFT_CERTIFICATE_TEST_DURATION = 10L;
    public static final Timestamp GIFT_CERTIFICATE_TEST_CREATE_DATE = new Timestamp(1640988061000L);
    public static final String GIFT_CERTIFICATE_TEST_CREATE_DATE_STRING = "2022-01-01T01:01:01";
    public static final Timestamp GIFT_CERTIFICATE_TEST_LAST_UPDATE_DATE = new Timestamp(1643666461000L);
    public static final String GIFT_CERTIFICATE_TEST_LAST_UPDATE_DATE_STRING = "2022-02-01T01:01:01";
    public static final BigDecimal GIFT_CERTIFICATE_TEST_PRICE = new BigDecimal(100);
    public static final String GIFT_CERTIFICATE_TEST_PRICE_STRING = "100";
    public static final int COUNT_TEST_TAG_LIST = 10;
    public static final int COUNT_TEST_GIFT_CERTIFICATE_LIST = 10;
    public static final int COUNT_TEST_TAG_DTO_LIST = 10;

    public static Tag getValidTag() {
        return Tag.builder()
                .id(TAG_TEST_ID)
                .name(TAG_TEST_NAME)
                .build();
    }

    public static TagDTO getValidTagDTO() {
        return TagDTO.builder()
                .id(TAG_TEST_ID)
                .name(TAG_TEST_NAME)
                .build();
    }

    public static GiftCertificate getValidGiftCertificate() {
        return GiftCertificate.builder()
                .id(GIFT_CERTIFICATE_TEST_ID)
                .name(GIFT_CERTIFICATE_TEST_NAME)
                .description(GIFT_CERTIFICATE_TEST_DESCRIPTION)
                .duration(GIFT_CERTIFICATE_TEST_DURATION)
                .createDate(GIFT_CERTIFICATE_TEST_CREATE_DATE)
                .lastUpdateDate(GIFT_CERTIFICATE_TEST_LAST_UPDATE_DATE)
                .price(GIFT_CERTIFICATE_TEST_PRICE)
                .tags(getValidTags(COUNT_TEST_TAG_LIST))
                .build();
    }

    public static GiftCertificateDTO getValidGiftCertificateDTO() {
        return GiftCertificateDTO.builder()
                .id(GIFT_CERTIFICATE_TEST_ID)
                .name(GIFT_CERTIFICATE_TEST_NAME)
                .description(GIFT_CERTIFICATE_TEST_DESCRIPTION)
                .duration(GIFT_CERTIFICATE_TEST_DURATION)
                .createDate(GIFT_CERTIFICATE_TEST_CREATE_DATE_STRING)
                .lastUpdateDate(GIFT_CERTIFICATE_TEST_LAST_UPDATE_DATE_STRING)
                .price(GIFT_CERTIFICATE_TEST_PRICE_STRING)
                .tags(getValidTagDTOs(COUNT_TEST_TAG_DTO_LIST))
                .build();
    }

    public static List<Tag> getValidTags(int count) {
        return Stream.generate(TestServiceDataUtil::getValidTag).limit(count).collect(Collectors.toList());
    }

    public static List<TagDTO> getValidTagDTOs(int count) {
        return Stream.generate(TestServiceDataUtil::getValidTagDTO).limit(count).collect(Collectors.toList());
    }

    public static List<GiftCertificate> getValidGiftCertificates(int count) {
        return Stream.generate(TestServiceDataUtil::getValidGiftCertificate).limit(count).collect(Collectors.toList());
    }

    public static List<GiftCertificateDTO> getValidGiftCertificateDTOs(int count) {
        return Stream.generate(TestServiceDataUtil::getValidGiftCertificateDTO).limit(count).collect(Collectors.toList());
    }
}