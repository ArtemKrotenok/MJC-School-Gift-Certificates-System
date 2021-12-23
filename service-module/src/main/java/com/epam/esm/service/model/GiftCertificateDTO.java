package com.epam.esm.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class GiftCertificateDTO {
    private String name;
    private String description;
    private String price;
    private Long duration;
    private String createDate;
    private String lastUpdateDate;
    private List<String> tagList;
}
