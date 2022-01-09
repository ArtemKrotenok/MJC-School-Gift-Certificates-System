package com.epam.esm.repository.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<Tag> tagList;

}
