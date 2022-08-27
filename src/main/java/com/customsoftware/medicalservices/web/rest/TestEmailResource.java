package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-email")
public class TestEmailResource {

    private final MailService mailService;

    public TestEmailResource(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize(AccessConstants.ADMIN)
    public void sendEmail(@RequestBody String to) {
        this.mailService.sendEmail(to, "Testing email", "This is a test.", false, false);
    }
}
