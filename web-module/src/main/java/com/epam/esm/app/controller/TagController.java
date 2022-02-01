package com.epam.esm.app.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.model.TagDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller class for <b>tag</b> (for gift certificate).
 *
 * @author Artem Krotenok
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor

public class TagController {

    public TagService tagService;

    /**
     * controller for create new tag
     *
     * @param tagDTO - object contain new tag model
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTag(@RequestBody TagDTO tagDTO) {
        tagService.create(tagDTO);
    }

    /**
     * controller for getting a list of tag by page using pagination (list sorted by name tag)
     *
     * @param page - number page for return
     * @return list TagDTO
     */
    @GetMapping
    public ResponseEntity<Object> getTagsByPageSorted(@RequestParam(name = "page", required = false) Integer page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getAllByPageSorted(page));
    }

    /**
     * controller for getting tag by id
     *
     * @param id - id number tag in database
     * @return TagDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.findById(id));
    }

    /**
     * controller for delete tag
     *
     * @param id - id number gift tag in database
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable(name = "id") Long id) {
        tagService.deleteById(id);
    }
}