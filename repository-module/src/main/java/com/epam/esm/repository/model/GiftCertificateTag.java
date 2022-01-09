package com.epam.esm.repository.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateTag {

    private Long idGiftCertificate;
    private Long idTag;
}