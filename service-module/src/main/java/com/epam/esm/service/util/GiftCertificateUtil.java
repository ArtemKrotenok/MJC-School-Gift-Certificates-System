package com.epam.esm.service.util;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.model.GiftCertificateDTO;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateUtil {

    public static GiftCertificateDTO covert(GiftCertificate giftCertificate) {
        return GiftCertificateDTO.builder()
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice().toString())
                .createDate(giftCertificate.getCreateDate().toString())
                .lastUpdateDate(giftCertificate.getLastUpdateDate().toString())
                .tagList(convertTagList(giftCertificate.getTagList()))
                .build();
    }

    private static List<String> convertTagList(List<Tag> tagList) {

        List<String> tagStringList = new ArrayList<>();
        if (tagList != null) {
            for (Tag tag : tagList) {
                tagStringList.add(tag.getName());
            }
        }
        return tagStringList;
    }
}
