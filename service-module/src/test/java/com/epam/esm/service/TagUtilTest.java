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
    void convert_givenTagDTO_returnsTag() {
        TagDTO validTagDTO = TestServiceDataUtil.getValidTagDTO();
        Tag validTag = TestServiceDataUtil.getValidTag();
        Tag covertTag = TagUtil.convert(validTagDTO);
        assertThat(covertTag).isEqualTo(validTag);
    }

    @Test
    void convert_givenInvalidTagDTO_returnsNull() {
        TagDTO nullTagDTO = null;
        Tag covertTagDTO = TagUtil.convert(nullTagDTO);
        assertThat(covertTagDTO).isNull();
    }

    @Test
    void convert_givenTag_returnsTagDTO() {
        Tag validTag = TestServiceDataUtil.getValidTag();
        TagDTO validTagDTO = TestServiceDataUtil.getValidTagDTO();
        TagDTO covertTagDTO = TagUtil.convert(validTag);
        assertThat(covertTagDTO).isEqualTo(validTagDTO);
    }

    @Test
    void convert_givenInvalidTag_returnsNull() {
        Tag nullTag = null;
        TagDTO covertTagDTO = TagUtil.convert(nullTag);
        assertThat(covertTagDTO).isNull();
    }
}