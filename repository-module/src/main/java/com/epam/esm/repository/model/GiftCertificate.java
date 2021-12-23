package com.epam.esm.repository.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long duration;
    private LocalDate createDate;
    private LocalDate lastUpdateDate;
    private List<Tag> tagList;

}
