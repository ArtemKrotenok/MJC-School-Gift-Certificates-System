package com.epam.esm.service;

import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.model.TagDTO;
import com.epam.esm.service.util.TagUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TagUtilTest {

    @Test
    void convert_DTO_to_entity() {
        TagDTO validTagDTO = TestDataUtil.getValidTagDTO();
        Tag validTag = TestDataUtil.getValidTag();
        Tag covertTag = TagUtil.convert(validTagDTO);
        assertThat(covertTag).isEqualTo(validTag);
    }

    @Test
    void convert_DTO_NULL_to_entity() {
        TagDTO nullTagDTO = null;
        Tag covertTagDTO = TagUtil.convert(nullTagDTO);
        assertThat(covertTagDTO).isNull();
    }

    @Test
    void convert_entity_to_DTO() {
        Tag validTag = TestDataUtil.getValidTag();
        TagDTO validTagDTO = TestDataUtil.getValidTagDTO();
        TagDTO covertTagDTO = TagUtil.convert(validTag);
        assertThat(covertTagDTO).isEqualTo(validTagDTO);
    }

    @Test
    void convert_entity_NULL_to_DTO() {
        Tag nullTag = null;
        TagDTO covertTagDTO = TagUtil.convert(nullTag);
        assertThat(covertTagDTO).isNull();
    }
}