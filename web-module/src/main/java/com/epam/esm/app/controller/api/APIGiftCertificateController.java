package com.epam.esm.app.controller.api;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.model.GiftCertificateDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificate", produces = MediaType.APPLICATION_JSON_VALUE)

@AllArgsConstructor
@Slf4j
public class APIGiftCertificateController {

    public GiftCertificateService giftCertificateService;

    @PostMapping
    public String addGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.add(giftCertificateDTO);
    }

    @PutMapping
    public String updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.update(giftCertificateDTO);
    }

    @GetMapping("/all")
    public String getGiftCertificateByPageSorted(@RequestParam(name = "page", required = false) Integer page) {
        return giftCertificateService.getAllGiftCertificateByPageSorted(page);
    }

    @GetMapping("/search")
    public String searchGiftCertificate(
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ) {
        return giftCertificateService.searchGiftCertificate(tag, name, description);
    }

    @GetMapping(value = "/{id}")
    public String getGiftCertificateById(@PathVariable(name = "id") Long id) {
        return giftCertificateService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteGiftCertificateById(@PathVariable(name = "id") Long id) {
        return giftCertificateService.deleteById(id);
    }

    @GetMapping("/start")
    public String start() {
        return "Server STARTED !";
    }
}
