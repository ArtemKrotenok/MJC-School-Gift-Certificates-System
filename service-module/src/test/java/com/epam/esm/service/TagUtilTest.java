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
    void convert_DTO_entity() {
        TagDTO validTagDTO = TestServiceDataUtil.getValidTagDTO();
        Tag validTag = TestServiceDataUtil.getValidTag();
        Tag covertTag = TagUtil.convert(validTagDTO);
        assertThat(covertTag).isEqualTo(validTag);
    }

    @Test
    void convert_invalidDTO_null() {
        TagDTO nullTagDTO = null;
        Tag covertTagDTO = TagUtil.convert(nullTagDTO);
        assertThat(covertTagDTO).isNull();
    }

    @Test
    void convert_entity_DTO() {
        Tag validTag = TestServiceDataUtil.getValidTag();
        TagDTO validTagDTO = TestServiceDataUtil.getValidTagDTO();
        TagDTO covertTagDTO = TagUtil.convert(validTag);
        assertThat(covertTagDTO).isEqualTo(validTagDTO);
    }

    @Test
    void convert_invalidEntity_null() {
        Tag nullTag = null;
        TagDTO covertTagDTO = TagUtil.convert(nullTag);
        assertThat(covertTagDTO).isNull();
    }
}