package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.service.OrganizationService;
import com.customsoftware.medicalservices.service.dto.OrganizationDTO;
import com.customsoftware.medicalservices.web.rest.util.HeaderUtil;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrganizationResource extends AbstractResource {

    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        super(OrganizationResource.class, "organization");
        this.organizationService = organizationService;
    }

    @PostMapping("/organization")
    @PreAuthorize(AccessConstants.ADMIN)
    public ResponseEntity<OrganizationDTO> save(@RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organizationDTO);
        OrganizationDTO result = organizationService.save(organizationDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(true, entityName, result.getId().toString())).body(result);
    }

    @GetMapping("/organization")
    @PreAuthorize(AccessConstants.ADMIN)
    public ResponseEntity<OrganizationDTO> getOrganization() {
        OrganizationDTO result = organizationService.getOrganization();
        return ResponseEntity.ok().body(result);
    }
}
