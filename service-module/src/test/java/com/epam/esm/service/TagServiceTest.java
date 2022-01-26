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
    void create_givenValidTag_returnsSuccessResponseDTO() throws GiftCertificateServiceException {
        TagDTO tagDTO = TestServiceDataUtil.getValidTagDTO();
        when(tagRepository.add(any(Tag.class))).thenReturn(1L);
        SuccessResponseDTO successResponseDTO = tagService.create(tagDTO);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.CREATE.getCode());
    }

    @Test
    void create_givenInvalidTag_returnsGiftCertificateServiceException() {
        TagDTO tagDTO = TestServiceDataUtil.getValidTagDTO();
        tagDTO.setName(null);
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            tagService.create(tagDTO);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_VALID_INPUT_DATA.getCode());
    }

    @Test
    void findById_givenValidId_returnsTagDTO() throws GiftCertificateServiceException {
        when(tagRepository.findById(TAG_TEST_ID)).thenReturn(TestServiceDataUtil.getValidTag());
        TagDTO finedTagDTO = tagService.findById(TAG_TEST_ID);
        assertEquals(finedTagDTO, TestServiceDataUtil.getValidTagDTO());
    }

    @Test
    void findById_givenInvalidId_returnsGiftCertificateServiceException() {
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            tagService.findById(TAG_TEST_ID_NOT_EXIST);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void deleteById_givenValidId_returnsSuccessResponseDTO() throws GiftCertificateServiceException {
        Tag validTag = TestServiceDataUtil.getValidTag();
        when(tagRepository.findById(TAG_TEST_ID)).thenReturn(validTag);
        when(tagRepository.delete(validTag)).thenReturn(ONE_RESULT);
        SuccessResponseDTO successResponseDTO = tagService.deleteById(TAG_TEST_ID);
        assertThat(successResponseDTO).isNotNull();
        assertEquals(successResponseDTO.getCode(), ResponseCode.DELETE.getCode());
    }

    @Test
    void deleteById_givenInvalidId_returnsGiftCertificateServiceException() {
        when(tagRepository.findById(TAG_TEST_ID_NOT_EXIST)).thenReturn(null);
        GiftCertificateServiceException exception = assertThrows(GiftCertificateServiceException.class, () -> {
            tagService.deleteById(TAG_TEST_ID_NOT_EXIST);
        });
        assertEquals(exception.getErrorResponseDTO().getErrorCode(),
                ResponseCode.NOT_FOUND.getCode());
    }

    @Test
    void getAllByPageSorted_returnsSortedTagDTOs() throws GiftCertificateServiceException {
        when(tagRepository.getAllByPageSorted(0, PaginationUtil.ITEMS_BY_PAGE)).
                thenReturn(TestServiceDataUtil.getValidTags(TestServiceDataUtil.COUNT_TEST_TAG_LIST));
        List<TagDTO> finedTagDTOList = tagService.getAllByPageSorted(1);
        assertEquals(finedTagDTOList, TestServiceDataUtil.getValidTagDTOs(COUNT_TEST_TAG_LIST));
    }

    @Test
    void getAllSorted_returnsSortedTagDTOs() throws GiftCertificateServiceException {
        when(tagRepository.getAllSorted()).
                thenReturn(TestServiceDataUtil.getValidTags(TestServiceDataUtil.COUNT_TEST_TAG_LIST));
        List<TagDTO> finedTagDTOList = tagService.getAllSorted();
        assertEquals(finedTagDTOList, TestServiceDataUtil.getValidTagDTOs(COUNT_TEST_TAG_LIST));
    }
}