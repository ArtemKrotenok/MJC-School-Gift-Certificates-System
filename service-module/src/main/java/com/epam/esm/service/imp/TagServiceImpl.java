package com.epam.esm.service.imp;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.SuccessResponseDTO;
import com.epam.esm.service.model.TagDTO;
import com.epam.esm.service.util.PaginationUtil;
import com.epam.esm.service.util.ResponseDTOUtil;
import com.epam.esm.service.util.TagUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    public static final int FIST_PAGE = 1;
    public static final int RESULT_ONE_RECORD = 1;
    private TagRepository tagRepository;

    @Override
    public SuccessResponseDTO create(TagDTO tagDTO) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValid(tagDTO);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        if (tagRepository.getTagByName(tagDTO.getName()) != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_CREATE, "tag by name: " + tagDTO.getName() + " already exists"));
        }
        if (tagRepository.add(TagUtil.convert(tagDTO)) != null) {
            return ResponseDTOUtil.getSuccessResponseDTO(
                    ResponseCode.CREATE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_CREATE));
    }

    @Override
    public TagDTO findById(Long id) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValid(id);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        Tag tag = tagRepository.findById(id);
        if (tag != null) {
            return TagUtil.convert(tag);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND, "for id=" + id), HttpStatus.NOT_FOUND);
    }

    @Override
    public SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValid(id);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        Tag tag = tagRepository.findById(id);
        if (tag == null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + id));
        }
        if (tagRepository.delete(tag) == RESULT_ONE_RECORD) {
            return ResponseDTOUtil.getSuccessResponseDTO(
                    ResponseCode.DELETE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_DELETE));
    }

    @Override
    public List<TagDTO> getAllTagsByPageSorted(Integer page) throws GiftCertificateServiceException {
        if (page < 0) {
            page = FIST_PAGE;
        }
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Tag> tagList = tagRepository.getAllTagByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return checkAndReturnResult(tagList);
    }

    @Override
    public List<TagDTO> getAllTagsSorted() throws GiftCertificateServiceException {
        List<Tag> tagList = tagRepository.getAllTagSorted();
        return checkAndReturnResult(tagList);
    }

    private List<TagDTO> checkAndReturnResult(List<Tag> tagList) throws GiftCertificateServiceException {
        if (!tagList.isEmpty()) {
            return tagList.stream().map(TagUtil::convert).collect(Collectors.toList());
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND));
    }

    private String getErrorValid(Long id) {
        if (id == null) {
            return ("id can't be empty");
        }
        if (id <= 0) {
            return ("id must > 0");
        }
        return null;
    }

    private String getErrorValid(TagDTO tagDTO) {
        if (tagDTO.getName() == null || tagDTO.getName().equals("")) {
            return ("tag name can't be empty");
        }
        return null;
    }
}