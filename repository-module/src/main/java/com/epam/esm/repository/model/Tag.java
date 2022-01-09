package com.epam.esm.repository.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
}
