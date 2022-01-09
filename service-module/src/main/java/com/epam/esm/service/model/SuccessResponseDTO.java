package com.epam.esm.service.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDTO {

    private String message;
    private Integer code;
}
