package com.epam.esm.service;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.imp.TagServiceImpl;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.SuccessResponseDTO;
import com.epam.esm.service.model.TagDTO;
import com.epam.esm.service.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.esm.service.TestServiceDataUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private static final int ONE_RESULT = 1;
    @Mock
    TagRepository tagRepository;
    TagService tagService;

    @BeforeEach
    public void setup() {
        this.tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    void creat_validTag_SuccessResponseDTO() throws GiftCertificateServiceException {
        TagDTO tagDTO = TestServiceDataUtil.getValidTagDTO();
        when(tagRepository.add(any(Tag.class))).thenReturn(1L);
        SuccessResponseDTO successResponseDTO = tagService.create(tagDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.CREATE.getCode());
    }

    @Test
    void create_invalidTag_GiftCertificateServiceException() {
        TagDTO tagDTO = TestServiceDataUtil.getValidTagDTO();
        tagDTO.setName(null);
        Exception exception = assertThrows(Exception.class, () -> {
            tagService.create(tagDTO);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_VALID_INPUT_DATA.getCode());
    }

    @Test
    void findById_validId_TagDTO() throws GiftCertificateServiceException {
        when(tagRepository.findById(TAG_TEST_ID)).thenReturn(TestServiceDataUtil.getValidTag());
        TagDTO finedTagDTO = tagService.findById(TAG_TEST_ID);
        assertEquals(finedTagDTO, TestServiceDataUtil.getValidTagDTO());
    }

    @Test
    void findById_invalidId_GiftCertificateServiceException() {
        Exception exception = assertThrows(Exception.class, () -> {
            tagService.findById(TAG_TEST_ID_NOT_EXIST);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void deleteById_validId_SuccessResponseDTO() throws GiftCertificateServiceException {
        Tag validTag = TestServiceDataUtil.getValidTag();
        when(tagRepository.findById(TAG_TEST_ID)).thenReturn(validTag);
        when(tagRepository.delete(validTag)).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = tagService.deleteById(TAG_TEST_ID);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.DELETE.getCode());
    }

    @Test
    void deleteById_invalidId_GiftCertificateServiceException() {
        when(tagRepository.findById(TAG_TEST_ID_NOT_EXIST)).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            tagService.deleteById(TAG_TEST_ID_NOT_EXIST);
        });
        assertEquals(GiftCertificateServiceException.class, exception.getClass());
        assertEquals(
                ((GiftCertificateServiceException) exception).getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void getAllByPageSorted_returnSortedTagDTOs() throws GiftCertificateServiceException {
        when(tagRepository.getAllByPageSorted(0, PaginationUtil.ITEMS_BY_PAGE)).
                thenReturn(TestServiceDataUtil.getValidTagList(TestServiceDataUtil.COUNT_TEST_TAG_LIST));
        List<TagDTO> finedTagDTOList = tagService.getAllByPageSorted(1);
        assertEquals(finedTagDTOList, TestServiceDataUtil.getValidTagDTOList(COUNT_TEST_TAG_LIST));
    }

    @Test
    void getAllSorted_returnSortedTagDTOs() throws GiftCertificateServiceException {
        when(tagRepository.getAllSorted()).
                thenReturn(TestServiceDataUtil.getValidTagList(TestServiceDataUtil.COUNT_TEST_TAG_LIST));
        List<TagDTO> finedTagDTOList = tagService.getAllSorted();
        assertEquals(finedTagDTOList, TestServiceDataUtil.getValidTagDTOList(COUNT_TEST_TAG_LIST));
    }
}