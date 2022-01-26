package com.epam.esm.app.controller;

import com.epam.esm.service.exception.GiftCertificateServiceException;
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
}