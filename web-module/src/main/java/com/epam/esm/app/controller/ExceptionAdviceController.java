package com.epam.esm.app.controller;

import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.ResponseCode;
import com.epam.esm.service.util.ResponseDTOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler({GiftCertificateServiceException.class})
    public ResponseEntity<Object> handleException(GiftCertificateServiceException exception) {
        return ResponseEntity
                .status(exception.getResponseHttpStatus())
                .body(exception.getErrorResponseDTO());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTOUtil.getErrorResponseDTO(ResponseCode.UNKNOWN, exception.getMessage()));
    }
}