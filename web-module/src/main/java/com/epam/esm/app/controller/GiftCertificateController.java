package com.epam.esm.app.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.model.GiftCertificateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller class for <b>gift certificate</b>.
 *
 * @author Artem Krotenok
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor

public class GiftCertificateController {

    public GiftCertificateService giftCertificateService;

    /**
     * controller for create new gift certificate
     *
     * @param giftCertificateDTO - object contain new gift certificate model
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.create(giftCertificateDTO);
    }

    /**
     * controller for update gift certificate
     *
     * @param giftCertificateDTO - object contain update data for gift certificate model
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.update(giftCertificateDTO);
    }

    /**
     * controller for getting a list of gift certificates by page (using pagination)
     * search by tag, name, description
     * sorted by name gift certificates
     *
     * @param page        - number page for return
     * @param tag         - one of tag for gift certificate (not required)
     * @param name        - name for gift certificate (not required)
     * @param description - description for gift certificate (not required)
     * @return list GiftCertificateDTO
     */
    @GetMapping
    public ResponseEntity<Object> searchGiftCertificates(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(giftCertificateService.search(page, tag, name, description));
    }

    /**
     * controller for getting gift certificate by id
     *
     * @param id - id number gift certificate in database
     * @return GiftCertificateDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getGiftCertificateById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(giftCertificateService.findById(id));
    }

    /**
     * controller for delete gift certificate
     *
     * @param id - id number gift certificate in database
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificateById(@PathVariable(name = "id") Long id) {
        giftCertificateService.deleteById(id);
    }
}