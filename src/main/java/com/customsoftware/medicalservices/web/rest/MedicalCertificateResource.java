package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.service.MedicalCertificateService;
import com.customsoftware.medicalservices.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.customsoftware.medicalservices.domain.MedicalCertificate}.
 */
@RestController
@RequestMapping("/api")
public class MedicalCertificateResource {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificateResource.class);

    private static final String ENTITY_NAME = "medicalCertificate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalCertificateService medicalCertificateService;

    private final MedicalCertificateRepository medicalCertificateRepository;

    public MedicalCertificateResource(
        MedicalCertificateService medicalCertificateService,
        MedicalCertificateRepository medicalCertificateRepository
    ) {
        this.medicalCertificateService = medicalCertificateService;
        this.medicalCertificateRepository = medicalCertificateRepository;
    }

    /**
     * {@code POST  /medical-certificates} : Create a new medicalCertificate.
     *
     * @param medicalCertificate the medicalCertificate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalCertificate, or with status {@code 400 (Bad Request)} if the medicalCertificate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-certificates")
    public ResponseEntity<MedicalCertificate> createMedicalCertificate(@RequestBody MedicalCertificate medicalCertificate)
        throws URISyntaxException {
        log.debug("REST request to save MedicalCertificate : {}", medicalCertificate);
        if (medicalCertificate.getId() != null) {
            throw new BadRequestAlertException("A new medicalCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalCertificate result = medicalCertificateService.save(medicalCertificate);
        return ResponseEntity
            .created(new URI("/api/medical-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-certificates/:id} : Updates an existing medicalCertificate.
     *
     * @param id the id of the medicalCertificate to save.
     * @param medicalCertificate the medicalCertificate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalCertificate,
     * or with status {@code 400 (Bad Request)} if the medicalCertificate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalCertificate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-certificates/{id}")
    public ResponseEntity<MedicalCertificate> updateMedicalCertificate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicalCertificate medicalCertificate
    ) throws URISyntaxException {
        log.debug("REST request to update MedicalCertificate : {}, {}", id, medicalCertificate);
        if (medicalCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicalCertificate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicalCertificateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicalCertificate result = medicalCertificateService.save(medicalCertificate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalCertificate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medical-certificates/:id} : Partial updates given fields of an existing medicalCertificate, field will ignore if it is null
     *
     * @param id the id of the medicalCertificate to save.
     * @param medicalCertificate the medicalCertificate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalCertificate,
     * or with status {@code 400 (Bad Request)} if the medicalCertificate is not valid,
     * or with status {@code 404 (Not Found)} if the medicalCertificate is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicalCertificate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medical-certificates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MedicalCertificate> partialUpdateMedicalCertificate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicalCertificate medicalCertificate
    ) throws URISyntaxException {
        log.debug("REST request to partial update MedicalCertificate partially : {}, {}", id, medicalCertificate);
        if (medicalCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicalCertificate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicalCertificateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicalCertificate> result = medicalCertificateService.partialUpdate(medicalCertificate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalCertificate.getId().toString())
        );
    }

    /**
     * {@code GET  /medical-certificates} : get all the medicalCertificates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalCertificates in body.
     */
    @GetMapping("/medical-certificates")
    public ResponseEntity<List<MedicalCertificate>> getAllMedicalCertificates(Pageable pageable) {
        log.debug("REST request to get a page of MedicalCertificates");
        Page<MedicalCertificate> page = medicalCertificateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-certificates/:id} : get the "id" medicalCertificate.
     *
     * @param id the id of the medicalCertificate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalCertificate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-certificates/{id}")
    public ResponseEntity<MedicalCertificate> getMedicalCertificate(@PathVariable Long id) {
        log.debug("REST request to get MedicalCertificate : {}", id);
        Optional<MedicalCertificate> medicalCertificate = medicalCertificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalCertificate);
    }

    /**
     * {@code DELETE  /medical-certificates/:id} : delete the "id" medicalCertificate.
     *
     * @param id the id of the medicalCertificate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-certificates/{id}")
    public ResponseEntity<Void> deleteMedicalCertificate(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCertificate : {}", id);
        medicalCertificateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
