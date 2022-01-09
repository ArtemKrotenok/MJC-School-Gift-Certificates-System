package com.epam.esm.service.imp;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.TagDTO;
import com.epam.esm.service.util.PaginationUtil;
import com.epam.esm.service.util.ResponseDTOUtil;
import com.epam.esm.service.util.TagUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ObjectMapper objectMapper;

    @Override
    public String add(TagDTO tagDTO) {
        String errorValidMessage = getErrorValid(tagDTO);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            if (tagRepository.getTagByName(tagDTO.getName()) != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_CREATE, "tag by name: " + tagDTO.getName() + " already exists"));
            }
            if (tagRepository.add(TagUtil.convert(tagDTO)) != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getSuccessResponseDTO(
                        ResponseCode.CREATE));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_CREATE));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String findById(Long id) {
        String errorValidMessage = getErrorValid(id);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            Tag tag = tagRepository.findById(id);
            if (tag != null) {
                return objectMapper.writeValueAsString(TagUtil.convert(tag));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + id));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String deleteById(Long id) {
        String errorValidMessage = getErrorValid(id);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            Tag tag = tagRepository.findById(id);
            if (tag == null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_FOUND, "for id=" + id));
            }
            if (tagRepository.delete(tag) == RESULT_ONE_RECORD) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getSuccessResponseDTO(
                        ResponseCode.DELETE));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_DELETE));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String getAllTagsByPageSorted(Integer page) {
        if (page == null) {
            return getAllTagsSorted();
        }
        if (page < 0) {
            page = FIST_PAGE;
        }
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Tag> tagList = tagRepository.getAllTagByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return checkAndReturnResult(tagList);
    }

    @Override
    public String getAllTagsSorted() {
        List<Tag> tagList = tagRepository.getAllTagSorted();
        return checkAndReturnResult(tagList);
    }

    private String checkAndReturnResult(List<Tag> tagList) {
        try {
            if (!tagList.isEmpty()) {
                return objectMapper.writeValueAsString(tagList.stream().map(TagUtil::convert).collect(Collectors.toList()));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
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
