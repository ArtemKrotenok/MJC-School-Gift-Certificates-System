package com.epam.esm.app.controller.api;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.GiftCertificateServiceException;
import com.epam.esm.service.model.GiftCertificateDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

/**
 * controller class for <b>gift certificate</b>.
 * @autor Artem Krotenok
 * @version 1.0
 */

public class GiftCertificateController {

    public GiftCertificateService giftCertificateService;

    /**
     * controller for create new gift certificate
     *
     * @param giftCertificateDTO - object contain new gift certificate model
     * @return if all success return object SuccessResponseDTO, else return object ErrorResponseDTO
     */
    @PostMapping
    public ResponseEntity<Object> createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(giftCertificateService.create(giftCertificateDTO));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for update gift certificate
     *
     * @param giftCertificateDTO - object contain update data for gift certificate model
     * @return if all success return object SuccessResponseDTO, else return object ErrorResponseDTO
     */
    @PutMapping
    public ResponseEntity<Object> updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftCertificateService.update(giftCertificateDTO));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for getting a list of gift certificates by page using pagination (list sorted by name gift certificates)
     *
     * @param page - number page for return
     * @return if all success return list GiftCertificateDTO, else return object ErrorResponseDTO
     */
    @GetMapping
    public ResponseEntity<Object> getGiftCertificateByPageSorted(@RequestParam(name = "page", required = false) Integer page) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftCertificateService.getAllGiftCertificateByPageSorted(page));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for getting gift certificate by id
     *
     * @param id - id number gift certificate in database
     * @return if all success return GiftCertificateDTO, else return object ErrorResponseDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getGiftCertificateById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftCertificateService.findById(id));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for search a list of gift certificates by tag, name, description (list sorted by name gift certificates)
     *
     * @param tag         - one of tag for gift certificate (not required)
     * @param name        - name for gift certificate (not required)
     * @param description - description for gift certificate (not required)
     * @return if all success return list GiftCertificateDTO, else return object ErrorResponseDTO
     */
    @GetMapping("/search")
    public ResponseEntity<Object> searchGiftCertificate(
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftCertificateService.searchGiftCertificate(tag, name, description));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    /**
     * controller for delete gift certificate
     *
     * @param id - id number gift certificate in database
     * @return if all success return object SuccessResponseDTO, else return object ErrorResponseDTO
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteGiftCertificateById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftCertificateService.deleteById(id));
        } catch (GiftCertificateServiceException exception) {
            return ResponseEntity
                    .status(exception.getResponseHttpStatus())
                    .body(exception.getErrorResponseDTO());
        }
    }

    //TODO delete this method
    @GetMapping("/start")
    public String start() {
        return "Server STARTED !";
    }
}
