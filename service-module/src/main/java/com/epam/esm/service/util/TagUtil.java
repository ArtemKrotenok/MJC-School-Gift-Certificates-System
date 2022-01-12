package com.epam.esm.service.util;

import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.model.TagDTO;

public class TagUtil {

    public static TagDTO convert(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static Tag convert(TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        }
        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
    }

}
