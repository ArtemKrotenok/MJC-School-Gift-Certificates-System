package com.epam.esm.repository.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {

    private Long id;
    private String name;
}
