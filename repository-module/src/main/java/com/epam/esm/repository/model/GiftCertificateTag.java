package com.epam.esm.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateTag {
    private Long idGiftCertificate;
    private Long idTag;
}