package com.epam.esm.app.controller.api;

import com.epam.esm.service.TagService;
import com.epam.esm.service.model.TagDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificate/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class APITagController {

    public TagService tagService;

    @PostMapping
    public String addTag(@RequestBody TagDTO tagDTO) {
        return tagService.add(tagDTO);
    }

    @GetMapping
    public String getTagsByPage(@RequestParam(name = "page", required = false) Integer page) {
        return tagService.getAllTagsByPageSorted(page);
    }

    @GetMapping(value = "/{id}")
    public String getTagById(@PathVariable(name = "id") Long id) {
        return tagService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTagById(@PathVariable(name = "id") Long id) {
        return tagService.deleteById(id);
    }

}
