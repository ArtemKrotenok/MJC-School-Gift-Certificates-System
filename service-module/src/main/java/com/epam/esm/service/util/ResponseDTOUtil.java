package com.epam.esm.service.util;

import com.epam.esm.service.model.ErrorResponseDTO;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.model.SuccessResponseDTO;

public class ResponseDTOUtil {

    public static ErrorResponseDTO getErrorResponseDTO(ResponseCode responseCode) {
        return ErrorResponseDTO.builder()
                .errorMessage(responseCode.getMessage())
                .errorCode(responseCode.getCode())
                .build();
    }

    public static ErrorResponseDTO getErrorResponseDTO(ResponseCode responseCode, String description) {
        return ErrorResponseDTO.builder()
                .errorMessage(responseCode.getMessage() + " (" + description + ")")
                .errorCode(responseCode.getCode())
                .build();
    }

    public static SuccessResponseDTO getSuccessResponseDTO(ResponseCode responseCode) {
        return SuccessResponseDTO.builder()
                .message(responseCode.getMessage())
                .code(responseCode.getCode())
                .build();
    }

}
