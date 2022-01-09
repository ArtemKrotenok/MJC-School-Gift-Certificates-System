package com.epam.esm.service.imp;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.util.GiftCertificateUtil;
import com.epam.esm.service.util.PaginationUtil;
import com.epam.esm.service.util.ResponseDTOUtil;
import com.epam.esm.service.util.TagUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final int FIST_PAGE = 1;
    public static final int RESULT_ONE_RECORD = 1;
    private GiftCertificateRepository giftCertificateRepository;
    private ObjectMapper objectMapper;

    @Override
    public String add(GiftCertificateDTO giftCertificateDTO) {

        String errorValidMessage = getErrorValid(giftCertificateDTO);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }

            if (giftCertificateRepository.add(GiftCertificateUtil.convert(giftCertificateDTO)) != null) {
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
    public String deleteById(Long id) {
        String errorValidMessage = getErrorValid(id);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
            if (giftCertificate == null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_FOUND, "for id=" + id));
            }
            if (giftCertificateRepository.delete(giftCertificate) == RESULT_ONE_RECORD) {
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
    public String findById(Long id) {
        String errorValidMessage = getErrorValid(id);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
            if (giftCertificate != null) {
                return objectMapper.writeValueAsString(GiftCertificateUtil.convert(giftCertificate));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + id));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String update(GiftCertificateDTO giftCertificateDTO) {
        Long updateIdGiftCertificate = giftCertificateDTO.getId();
        String errorValidMessage = getErrorValid(updateIdGiftCertificate);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            GiftCertificate giftCertificate = giftCertificateRepository.findById(updateIdGiftCertificate);
            if (giftCertificate == null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_FOUND, "for id=" + updateIdGiftCertificate));
            }
            giftCertificate = compareForUpdate(giftCertificate, giftCertificateDTO);
            if (giftCertificateRepository.update(giftCertificate) == RESULT_ONE_RECORD) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getSuccessResponseDTO(
                        ResponseCode.UPDATE));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_UPDATE));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String searchGiftCertificate(String tag, String name, String description) {
        try {
            if (tag == null && name == null && description == null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, "all search criteria are empty"));
            }
            List<GiftCertificate> giftCertificateResultList = new ArrayList<>();
            if (tag != null) {
                giftCertificateResultList.addAll(getNewElements(giftCertificateResultList,
                        giftCertificateRepository.findByTag(tag)));
            }
            if (name != null) {
                giftCertificateResultList.addAll(getNewElements(giftCertificateResultList,
                        giftCertificateRepository.findByName(name)));
            }
            if (description != null) {
                giftCertificateResultList.addAll(getNewElements(giftCertificateResultList,
                        giftCertificateRepository.findByDescription(description)));
            }
            if (giftCertificateResultList.isEmpty()) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_FOUND, "for all search criteria"));
            }
            return objectMapper.writeValueAsString(giftCertificateResultList
                    .stream()
                    .map(GiftCertificateUtil::convert)
                    .collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String getCertificateByTag(String tag) {
        String errorValidMessage = getErrorValid(tag);
        try {
            if (errorValidMessage != null) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
            }
            List<GiftCertificate> giftCertificateList = giftCertificateRepository.findByTag(tag);
            if (giftCertificateList.isEmpty()) {
                return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                        ResponseCode.NOT_FOUND, "for tag=" + tag));
            }
            return objectMapper.writeValueAsString(giftCertificateList
                    .stream()
                    .map(GiftCertificateUtil::convert)
                    .collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }

    @Override
    public String getAllGiftCertificateByPageSorted(Integer page) {
        if (page == null || page < 0) {
            page = FIST_PAGE;
        }
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.getAllGiftCertificateByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return checkAndReturnResult(giftCertificateList);
    }

    private List<GiftCertificate> getNewElements(List<GiftCertificate> giftCertificateResultList, List<GiftCertificate> giftCertificateNewList) {
        List<GiftCertificate> newElementList = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificateNewList) {
            if (!giftCertificateResultList.contains(giftCertificate)) {
                newElementList.add(giftCertificate);
            }
        }
        return newElementList;
    }

    private GiftCertificate compareForUpdate(GiftCertificate giftCertificate, GiftCertificateDTO giftCertificateDTO) {
        String updateName = giftCertificateDTO.getName();
        if (updateName != null && !updateName.equals("")) {
            giftCertificate.setName(updateName);
        }
        String updateDescription = giftCertificateDTO.getDescription();
        if (updateDescription != null && !updateDescription.equals("")) {
            giftCertificate.setDescription(updateDescription);
        }
        String updatePrice = giftCertificateDTO.getPrice();
        if (updatePrice != null && !updatePrice.equals("")) {
            giftCertificate.setPrice(new BigDecimal(updatePrice));
        }
        Long updateDuration = giftCertificateDTO.getDuration();
        if (updateDuration != null && updateDuration >= 0) {
            giftCertificate.setDuration(updateDuration);
        }
        String updateCreateDate = giftCertificateDTO.getCreateDate();
        if (updateCreateDate != null && !updateCreateDate.equals("")) {
            giftCertificate.setCreateDate(GiftCertificateUtil.dateConvert(updateCreateDate));
        }
        String updateLastUpdateDate = giftCertificateDTO.getLastUpdateDate();
        if (updateLastUpdateDate != null && !updateLastUpdateDate.equals("")) {
            giftCertificate.setCreateDate(GiftCertificateUtil.dateConvert(updateLastUpdateDate));
        }
        if (giftCertificateDTO.getTagList() != null) {
            giftCertificate.setTagList(giftCertificateDTO.getTagList().stream().map(TagUtil::convert).collect(Collectors.toList()));
        }
        return giftCertificate;
    }

    private String getErrorValid(String data) {
        if (data == null || data.equals("")) {
            return ("tag can't be empty");
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

    private String getErrorValid(GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().equals("")) {
            return ("certificate name can't be empty");
        }
        if (giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().equals("")) {
            return ("certificate description can't be empty");
        }
        if (giftCertificateDTO.getPrice() == null) {
            return ("certificate price can't be empty");
        }
        if (giftCertificateDTO.getDuration() == null || giftCertificateDTO.getDuration() <= 0) {
            return ("certificate duration should be > 0");
        }
        return null;
    }

    private String checkAndReturnResult(List<GiftCertificate> giftCertificateList) {
        try {
            if (!giftCertificateList.isEmpty()) {
                return objectMapper.writeValueAsString(giftCertificateList.stream().map(GiftCertificateUtil::convert).collect(Collectors.toList()));
            }
            return objectMapper.writeValueAsString(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND));
        } catch (JsonProcessingException e) {
            log.error("exception: " + e);
        }
        return null;
    }
}