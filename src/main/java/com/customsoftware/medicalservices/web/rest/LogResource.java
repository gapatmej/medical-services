package com.customsoftware.medicalservices.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LogResource {

    protected final Logger log = LoggerFactory.getLogger(LogResource.class);

    @GetMapping("/external/test1")
    public ResponseEntity<Void> test1() {
        log.debug("REST debug");
        log.error("REST error");
        log.info("REST info");
        System.out.println("REST System log");
        return null;
    }

    @GetMapping("/external/test2")
    public ResponseEntity<Void> test2() {
        int a = 5 / 0;
        return null;
    }
}
