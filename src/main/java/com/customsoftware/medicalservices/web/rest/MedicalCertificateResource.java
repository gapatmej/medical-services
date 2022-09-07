package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.security.SecurityUtils;
import com.customsoftware.medicalservices.service.MedicalCertificateService;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import com.customsoftware.medicalservices.web.rest.errors.BadRequestAlertException;
import com.customsoftware.medicalservices.web.rest.util.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class MedicalCertificateResource extends AbstractResource {

    private final MedicalCertificateService medicalCertificateService;

    private final MedicalCertificateRepository medicalCertificateRepository;

    public MedicalCertificateResource(
        MedicalCertificateService medicalCertificateService,
        MedicalCertificateRepository medicalCertificateRepository
    ) {
        super(MedicalCertificateResource.class, "medicalCertificate");
        this.medicalCertificateService = medicalCertificateService;
        this.medicalCertificateRepository = medicalCertificateRepository;
    }

    @PostMapping("/medical-certificates")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<MedicalCertificateDTO> createMedicalCertificate(@RequestBody MedicalCertificateDTO medicalCertificateDTO)
        throws URISyntaxException {
        log.debug("REST request to save MedicalCertificate : {}", medicalCertificateDTO);
        if (medicalCertificateDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalCertificate cannot already have an ID", entityName, "idexists");
        }
        MedicalCertificateDTO result = medicalCertificateService.save(medicalCertificateDTO);
        return ResponseEntity
            .created(new URI("/api/medical-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(true, entityName, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/medical-certificates")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<MedicalCertificateDTO> updateMedicalCertificate(@RequestBody MedicalCertificateDTO medicalCertificateDTO) {
        log.debug("REST request to update MedicalCertificate : {}, {}", medicalCertificateDTO.getId(), medicalCertificateDTO);
        if (medicalCertificateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", entityName, "idnull");
        }

        if (!medicalCertificateRepository.existsById(medicalCertificateDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", entityName, "idnotfound");
        }

        MedicalCertificateDTO result = medicalCertificateService.save(medicalCertificateDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(true, entityName, result.getId().toString())).body(result);
    }

    @PostMapping("/admin/medical-certificates/search")
    @PreAuthorize(AccessConstants.ADMIN)
    public ResponseEntity<List<MedicalCertificateDTO>> adminSearch(
        Pageable pageable,
        @RequestBody SearchMedicalCertificateDTO searchMedicalCertificateDTO
    ) {
        return search(pageable, searchMedicalCertificateDTO);
    }

    @PostMapping("/medical-certificates/doctor/search")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<List<MedicalCertificateDTO>> doctorSearch(
        Pageable pageable,
        @RequestBody SearchMedicalCertificateDTO searchMedicalCertificateDTO
    ) {
        searchMedicalCertificateDTO.setDoctorLogin(SecurityUtils.currentUserLogin());
        return search(pageable, searchMedicalCertificateDTO);
    }

    @PostMapping("/medical-certificates/patient/search")
    @PreAuthorize(AccessConstants.PATIENT)
    public ResponseEntity<List<MedicalCertificateDTO>> patientSearch(
        Pageable pageable,
        @RequestBody SearchMedicalCertificateDTO searchMedicalCertificateDTO
    ) {
        searchMedicalCertificateDTO.setPatientLogin(SecurityUtils.currentUserLogin());
        return search(pageable, searchMedicalCertificateDTO);
    }

    private ResponseEntity<List<MedicalCertificateDTO>> search(
        Pageable pageable,
        @RequestBody SearchMedicalCertificateDTO searchMedicalCertificateDTO
    ) {
        Page<MedicalCertificateDTO> page = medicalCertificateService.search(pageable, searchMedicalCertificateDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/medical-certificates/{id}")
    public ResponseEntity<MedicalCertificateDTO> searchById(@PathVariable Long id) {
        log.debug("REST request to get MedicalCertificate : {}", id);
        Optional<MedicalCertificateDTO> result = medicalCertificateService.searchById(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @DeleteMapping("/medical-certificates/{id}")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<Void> deleteMedicalCertificate(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCertificate : {}", id);
        medicalCertificateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(true, entityName, id.toString())).build();
    }

    @PostMapping("/medical-certificates/sign/{id}")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<List<MedicalCertificateDTO>> sign(@PathVariable Long id) {
        medicalCertificateService.sign(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(true, entityName, id.toString())).build();
    }

    @GetMapping("/medical-certificates/download-signed-certificate/{id}")
    @PreAuthorize(AccessConstants.DOCTOR_PATIENT)
    public ResponseEntity<InputStreamResource> downloadSignedCertificate(@PathVariable Long id) {
        ResponseEntity<InputStreamResource> result = medicalCertificateService.getSignedCertificate(id);
        return result;
    }
}
