package com.epam.esm.app.controller.api;

import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.TagDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificate/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

/**
 * controller class for <b>tag</b> (for gift certificate).
 * @autor Artem Krotenok
 * @version 1.0
 */

public class TagController {

    public TagService tagService;

    /**
     * controller for create new tag
     *
     * @param tagDTO - object contain new tag model
     * @return if all success return object SuccessResponseDTO, else return object ErrorResponseDTO
     */
    @PostMapping
    public ResponseEntity<Object> creatTag(@RequestBody TagDTO tagDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(tagService.create(tagDTO));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for getting a list of tag by page using pagination (list sorted by name tag)
     *
     * @param page - number page for return
     * @return if all success return list TagDTO, else return object ErrorResponseDTO
     */
    @GetMapping
    public ResponseEntity<Object> getTagsByPageSorted(@RequestParam(name = "page", required = false) Integer page) {
        try {
            if (page == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(tagService.getAllTagsSorted());
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tagService.getAllTagsByPageSorted(page));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for getting tag by id
     *
     * @param id - id number tag in database
     * @return if all success return TagDTO, else return object ErrorResponseDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tagService.findById(id));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for delete tag
     *
     * @param id - id number gift tag in database
     * @return if all success return object SuccessResponseDTO, else return object ErrorResponseDTO
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTagById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tagService.deleteById(id));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }
}