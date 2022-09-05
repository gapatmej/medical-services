package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.web.rest.errors.BadRequestAlertException;
import com.customsoftware.medicalservices.web.rest.errors.MedicalServicesRuntimeException;
import com.customsoftware.medicalservices.web.rest.util.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        throw new BadRequestAlertException("aaaa", null, "");
        //  return null;
    }

    @GetMapping("/external/test2")
    public ResponseEntity<Void> test2() {
        int a = 5 / 0;
        return null;
    }
}
