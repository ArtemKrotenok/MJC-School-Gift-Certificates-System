package com.epam.esm.service.imp;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.GiftCertificateDTO;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.SuccessResponseDTO;
import com.epam.esm.service.util.GiftCertificateUtil;
import com.epam.esm.service.util.PaginationUtil;
import com.epam.esm.service.util.ResponseDTOUtil;
import com.epam.esm.service.util.TagUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final int FIST_PAGE = 1;
    public static final int RESULT_ONE_RECORD = 1;
    private GiftCertificateRepository giftCertificateRepository;

    @Override
    public SuccessResponseDTO create(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValidMessage(giftCertificateDTO);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        if (giftCertificateRepository.add(GiftCertificateUtil.convert(giftCertificateDTO)) != null) {
            return ResponseDTOUtil.getSuccessResponseDTO(
                    ResponseCode.CREATE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_CREATE));
    }

    @Override
    public List<GiftCertificateDTO> getAllGiftCertificateByPageSorted(Integer page) throws GiftCertificateServiceException {
        if (page == null || page < 0) {
            page = FIST_PAGE;
        }
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.getAllGiftCertificateByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return checkAndReturnResult(giftCertificateList, false);
    }

    @Override
    public SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValidMessage(id);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        if (giftCertificate == null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + id));
        }
        if (giftCertificateRepository.delete(giftCertificate) == RESULT_ONE_RECORD) {
            return ResponseDTOUtil.getSuccessResponseDTO(
                    ResponseCode.DELETE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_DELETE));
    }

    @Override
    public SuccessResponseDTO update(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException {
        Long updateIdGiftCertificate = giftCertificateDTO.getId();
        String errorValidMessage = getErrorValidMessage(updateIdGiftCertificate);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        GiftCertificate giftCertificate = giftCertificateRepository.findById(updateIdGiftCertificate);
        if (giftCertificate == null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + updateIdGiftCertificate));
        }
        giftCertificate = compareForUpdate(giftCertificate, giftCertificateDTO);
        if (giftCertificateRepository.update(giftCertificate) == RESULT_ONE_RECORD) {
            return ResponseDTOUtil.getSuccessResponseDTO(ResponseCode.UPDATE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_UPDATE));
    }

    @Override
    public GiftCertificateDTO findById(Long id) throws GiftCertificateServiceException {
        String errorValidMessage = getErrorValidMessage(id);
        if (errorValidMessage != null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorValidMessage));
        }
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        if (giftCertificate != null) {
            return GiftCertificateUtil.convert(giftCertificate);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND, "for id=" + id));
    }

    @Override
    public List<GiftCertificateDTO> searchGiftCertificate(String tag, String name, String description) throws GiftCertificateServiceException {
        if (tag == null && name == null && description == null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
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
        return checkAndReturnResult(giftCertificateResultList, true);
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

    private String getErrorValidMessage(Long id) {
        if (id == null) {
            return ("id can't be empty");
        }
        if (id <= 0) {
            return ("id must > 0");
        }
        return null;
    }

    private String getErrorValidMessage(GiftCertificateDTO giftCertificateDTO) {
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

    private List<GiftCertificateDTO> checkAndReturnResult(List<GiftCertificate> giftCertificateList, boolean needSort) throws GiftCertificateServiceException {
        if (!giftCertificateList.isEmpty()) {
            if (needSort) {
                return sortResult(giftCertificateList.stream().map(GiftCertificateUtil::convert).collect(Collectors.toList()));
            }
            return giftCertificateList.stream().map(GiftCertificateUtil::convert).collect(Collectors.toList());
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    private List<GiftCertificateDTO> sortResult(List<GiftCertificateDTO> giftCertificateDTOList) {
        giftCertificateDTOList.sort(Comparator.comparing(GiftCertificateDTO::getName));
        return giftCertificateDTOList;
    }
}