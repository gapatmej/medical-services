package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.service.POCSignService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external/poc-sign")
public class POCSignResource {

    private final POCSignService pocSignService;

    public POCSignResource(POCSignService pocSignService) {
        this.pocSignService = pocSignService;
    }

    @PostMapping("/sign1")
    @ResponseStatus(HttpStatus.CREATED)
    public void sign1() {
        pocSignService.sign();
    }
}
