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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    public static final int RESULT_ONE_RECORD = 1;
    private GiftCertificateRepository giftCertificateRepository;

    @Override
    public SuccessResponseDTO create(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException {
        validation(giftCertificateDTO);
        if (giftCertificateRepository.add(GiftCertificateUtil.convert(giftCertificateDTO)) != null) {
            return ResponseDTOUtil.getSuccessResponseDTO(
                    ResponseCode.CREATE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_CREATE));
    }

    @Override
    public SuccessResponseDTO deleteById(Long id) throws GiftCertificateServiceException {
        validation(id);
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
        validation(updateIdGiftCertificate);
        GiftCertificate giftCertificate = giftCertificateRepository.findById(updateIdGiftCertificate);
        if (giftCertificate == null) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_FOUND, "for id=" + updateIdGiftCertificate));
        }
        giftCertificate = getEntityForUpdate(giftCertificate, giftCertificateDTO);
        if (giftCertificateRepository.update(giftCertificate) == RESULT_ONE_RECORD) {
            return ResponseDTOUtil.getSuccessResponseDTO(ResponseCode.UPDATE);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_UPDATE));
    }

    @Override
    public GiftCertificateDTO findById(Long id) throws GiftCertificateServiceException {
        validation(id);
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        if (giftCertificate != null) {
            return GiftCertificateUtil.convert(giftCertificate);
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND, "for id=" + id));
    }

    @Override
    public List<GiftCertificateDTO> search(Integer page, String tag, String name, String description) throws GiftCertificateServiceException {
        validation(page);
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<GiftCertificate> giftCertificates = giftCertificateRepository.search(startPosition, PaginationUtil.ITEMS_BY_PAGE, tag, name, description);
        return convertResults(giftCertificates);
    }

    private GiftCertificate getEntityForUpdate(GiftCertificate giftCertificate, GiftCertificateDTO giftCertificateDTO) {
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
        if (giftCertificateDTO.getTags() != null) {
            giftCertificate.setTags(giftCertificateDTO.getTags().stream().map(TagUtil::convert).collect(Collectors.toList()));
        }
        return giftCertificate;
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

    private void validation(GiftCertificateDTO giftCertificateDTO) throws GiftCertificateServiceException {
        StringBuilder errorMessage = new StringBuilder();

        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().equals("")) {
            errorMessage.append("certificate name can't be empty");
        }
        if (giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().equals("")) {
            errorMessage.append("certificate description can't be empty");
        }
        if (giftCertificateDTO.getTags() == null || giftCertificateDTO.getTags().isEmpty()) {
            errorMessage.append("certificate tag list can't be empty");
        }
        if (giftCertificateDTO.getPrice() == null) {
            errorMessage.append("certificate price can't be empty");
        }
        if (giftCertificateDTO.getDuration() == null || giftCertificateDTO.getDuration() <= 0) {
            errorMessage.append("certificate duration should be > 0");
        }
        if (!errorMessage.toString().isEmpty()) {
            throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                    ResponseCode.NOT_VALID_INPUT_DATA, errorMessage.toString()));
        }
    }

    private List<GiftCertificateDTO> convertResults(List<GiftCertificate> giftCertificates) throws GiftCertificateServiceException {
        if (!giftCertificates.isEmpty()) {
            return giftCertificates.stream().map(GiftCertificateUtil::convert).collect(Collectors.toList());
        }
        throw new GiftCertificateServiceException(ResponseDTOUtil.getErrorResponseDTO(
                ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}