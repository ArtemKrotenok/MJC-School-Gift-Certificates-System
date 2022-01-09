package com.epam.esm.service.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private Long id;
    private String name;
}
