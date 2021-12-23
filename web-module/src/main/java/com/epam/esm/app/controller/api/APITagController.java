package com.epam.esm.app.controller.api;

import com.epam.esm.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class APITagController {
    private final ObjectMapper objectMapper;
    public TagService tagService;

    @GetMapping("/tags")
    public String getAllTag() {
        try {
            return objectMapper.writeValueAsString(tagService.getAll());
        } catch (JsonProcessingException e) {
            return "error: " + e;
        }
    }
}
