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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    public static final int RESULT_ONE_RECORD = 1;
    private TagRepository tagRepository;

    @Override
    public SuccessResponseDTO create(TagDTO tagDTO) throws GiftCertificateServiceException {
        validation(tagDTO);
        if (tagRepository.findByName(tagDTO.getName()) != null) {
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
        validation(id);
        Tag tag = tagRepository.findById(id);
        if (tag != null) {
            return TagUtil.convert(tag);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND, "for id=" + id), HttpStatus.NOT_FOUND);
    }

    @Override
    public SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException {
        validation(id);
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
    public List<TagDTO> getAllByPageSorted(Integer page) throws GiftCertificateServiceException {
        validation(page);
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Tag> tags = tagRepository.getAllByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return convertResults(tags);
    }

    @Override
    public List<TagDTO> getAllSorted() throws GiftCertificateServiceException {
        List<Tag> tags = tagRepository.getAllSorted();
        return convertResults(tags);
    }

    private List<TagDTO> convertResults(List<Tag> tags) throws GiftCertificateServiceException {
        if (!tags.isEmpty()) {
            return tags.stream().map(TagUtil::convert).collect(Collectors.toList());
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND));
    }

    private void validation(Integer page) throws GiftCertificateServiceException {
        String errorMessage = null;
        if (page == null) {
            errorMessage = "page can't be empty";
        } else {
            if (page <= 0) {
                errorMessage = "page must > 0";
            }
        }
        if (errorMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorMessage));
        }
    }

    private void validation(Long id) throws GiftCertificateServiceException {
        String errorMessage = null;
        if (id == null) {
            errorMessage = "id can't be empty";
        } else {
            if (id <= 0) {
                errorMessage = "id must > 0";
            }
        }
        if (errorMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorMessage));
        }
    }

    private void validation(TagDTO tagDTO) throws GiftCertificateServiceException {
        String errorMessage = null;
        if (tagDTO.getName() == null || tagDTO.getName().equals("")) {
            errorMessage = "tag name can't be empty";
        }
        if (errorMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorMessage));
        }
    }
}