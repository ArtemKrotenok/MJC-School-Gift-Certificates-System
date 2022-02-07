package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRepositoryDataUtil {

    public static final long TAG_TEST_ID = 5L;
    public static final String TAG_TEST_NAME = "tag_name";
    public static final long GIFT_CERTIFICATE_TEST_ID = 10L;
    public static final String GIFT_CERTIFICATE_TEST_NAME = "certificate_name";
    public static final String GIFT_CERTIFICATE_TEST_DESCRIPTION = "description_certificate";
    public static final long GIFT_CERTIFICATE_TEST_DURATION = 10L;
    public static final Timestamp GIFT_CERTIFICATE_TEST_CREATE_DATE = new Timestamp(1640988061000L);
    public static final Timestamp GIFT_CERTIFICATE_TEST_LAST_UPDATE_DATE = new Timestamp(1643666461000L);
    public static final BigDecimal GIFT_CERTIFICATE_TEST_PRICE = new BigDecimal(100);
    public static final int COUNT_TEST_TAG_LIST = 10;

    public static Tag getValidTag() {
        return Tag.builder()
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

    public static List<Tag> getValidTags(int count) {
        return Stream.generate(TestRepositoryDataUtil::getValidTag).limit(count).collect(Collectors.toList());
    }
}