package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.domain.MedicalCertificade;
import com.customsoftware.medicalservices.repository.MedicalCertificadeRepository;
import com.customsoftware.medicalservices.service.MedicalCertificadeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.customsoftware.medicalservices.domain.MedicalCertificade}.
 */
@RestController
@RequestMapping("/api")
public class MedicalCertificadeResource {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificadeResource.class);

    private static final String ENTITY_NAME = "medicalCertificade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalCertificadeService medicalCertificadeService;

    private final MedicalCertificadeRepository medicalCertificadeRepository;

    public MedicalCertificadeResource(
        MedicalCertificadeService medicalCertificadeService,
        MedicalCertificadeRepository medicalCertificadeRepository
    ) {
        this.medicalCertificadeService = medicalCertificadeService;
        this.medicalCertificadeRepository = medicalCertificadeRepository;
    }

    /**
     * {@code POST  /medical-certificades} : Create a new medicalCertificade.
     *
     * @param medicalCertificade the medicalCertificade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalCertificade, or with status {@code 400 (Bad Request)} if the medicalCertificade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-certificades")
    public ResponseEntity<MedicalCertificade> createMedicalCertificade(@RequestBody MedicalCertificade medicalCertificade)
        throws URISyntaxException {
        log.debug("REST request to save MedicalCertificade : {}", medicalCertificade);
        if (medicalCertificade.getId() != null) {
            throw new BadRequestAlertException("A new medicalCertificade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalCertificade result = medicalCertificadeService.save(medicalCertificade);
        return ResponseEntity
            .created(new URI("/api/medical-certificades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-certificades/:id} : Updates an existing medicalCertificade.
     *
     * @param id the id of the medicalCertificade to save.
     * @param medicalCertificade the medicalCertificade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalCertificade,
     * or with status {@code 400 (Bad Request)} if the medicalCertificade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalCertificade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-certificades/{id}")
    public ResponseEntity<MedicalCertificade> updateMedicalCertificade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicalCertificade medicalCertificade
    ) throws URISyntaxException {
        log.debug("REST request to update MedicalCertificade : {}, {}", id, medicalCertificade);
        if (medicalCertificade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicalCertificade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicalCertificadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicalCertificade result = medicalCertificadeService.save(medicalCertificade);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalCertificade.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medical-certificades/:id} : Partial updates given fields of an existing medicalCertificade, field will ignore if it is null
     *
     * @param id the id of the medicalCertificade to save.
     * @param medicalCertificade the medicalCertificade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalCertificade,
     * or with status {@code 400 (Bad Request)} if the medicalCertificade is not valid,
     * or with status {@code 404 (Not Found)} if the medicalCertificade is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicalCertificade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medical-certificades/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MedicalCertificade> partialUpdateMedicalCertificade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicalCertificade medicalCertificade
    ) throws URISyntaxException {
        log.debug("REST request to partial update MedicalCertificade partially : {}, {}", id, medicalCertificade);
        if (medicalCertificade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicalCertificade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicalCertificadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicalCertificade> result = medicalCertificadeService.partialUpdate(medicalCertificade);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalCertificade.getId().toString())
        );
    }

    /**
     * {@code GET  /medical-certificades} : get all the medicalCertificades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalCertificades in body.
     */
    @GetMapping("/medical-certificades")
    public ResponseEntity<List<MedicalCertificade>> getAllMedicalCertificades(Pageable pageable) {
        log.debug("REST request to get a page of MedicalCertificades");
        Page<MedicalCertificade> page = medicalCertificadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-certificades/:id} : get the "id" medicalCertificade.
     *
     * @param id the id of the medicalCertificade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalCertificade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-certificades/{id}")
    public ResponseEntity<MedicalCertificade> getMedicalCertificade(@PathVariable Long id) {
        log.debug("REST request to get MedicalCertificade : {}", id);
        Optional<MedicalCertificade> medicalCertificade = medicalCertificadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalCertificade);
    }

    /**
     * {@code DELETE  /medical-certificades/:id} : delete the "id" medicalCertificade.
     *
     * @param id the id of the medicalCertificade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-certificades/{id}")
    public ResponseEntity<Void> deleteMedicalCertificade(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCertificade : {}", id);
        medicalCertificadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
