package com.epam.esm.service.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GiftCertificateDTO {

    private Long id;
    private String name;
    private String description;
    private String price;
    private Long duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDTO> tagList;
}
