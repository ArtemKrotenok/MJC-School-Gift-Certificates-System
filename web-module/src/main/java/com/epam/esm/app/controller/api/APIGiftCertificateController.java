package com.epam.esm.app.controller.api;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class APIGiftCertificateController {
    private final ObjectMapper objectMapper;
    public GiftCertificateService giftCertificateService;

    @GetMapping("/")
    public String getCertificateByTag() {
        try {
            return objectMapper.writeValueAsString(giftCertificateService.getCertificateByTag("all"));
        } catch (JsonProcessingException e) {
            return "error: " + e;
        }
    }
}
