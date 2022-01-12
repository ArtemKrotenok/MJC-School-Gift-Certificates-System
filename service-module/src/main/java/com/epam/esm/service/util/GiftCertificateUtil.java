package com.epam.esm.service.util;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.model.GiftCertificateDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class GiftCertificateUtil {

    public static final String DEFAULT_TIME_ZONE = "UTC";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static GiftCertificateDTO convert(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            return null;
        }
        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTO.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice().toString())
                .createDate(dateConvert(giftCertificate.getCreateDate()))
                .lastUpdateDate(dateConvert(giftCertificate.getLastUpdateDate()))
                .build();
        if (giftCertificate.getTagList() != null) {
            giftCertificateDTO.setTagList(giftCertificate.getTagList().stream().map(TagUtil::convert).collect(Collectors.toList()));
        }
        return giftCertificateDTO;
    }

    public static GiftCertificate convert(GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDTO == null) {
            return null;
        }
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(giftCertificateDTO.getName())
                .description(giftCertificateDTO.getDescription())
                .duration(giftCertificateDTO.getDuration())
                .tagList(giftCertificateDTO.getTagList().stream().map(TagUtil::convert).collect(Collectors.toList()))
                .build();
        BigDecimal price = new BigDecimal(giftCertificateDTO.getPrice());
        giftCertificate.setPrice(price);
        if (giftCertificateDTO.getCreateDate() == null) {
            giftCertificate.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            giftCertificate.setCreateDate(dateConvert(giftCertificateDTO.getCreateDate()));
        }
        if (giftCertificateDTO.getLastUpdateDate() == null) {
            giftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            giftCertificate.setLastUpdateDate(dateConvert(giftCertificateDTO.getLastUpdateDate()));
        }
        return giftCertificate;
    }

    public static String dateConvert(Timestamp date) {
        TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE);
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static Timestamp dateConvert(String date) {
        return Timestamp.valueOf(LocalDateTime.parse(date));
    }
}
