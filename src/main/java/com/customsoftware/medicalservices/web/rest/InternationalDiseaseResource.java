package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.service.InternationalDiseaseService;
import com.customsoftware.medicalservices.service.dto.InternationalDiseaseDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchInternationalDiseaseDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class InternationalDiseaseResource extends AbstractResource {

    private final InternationalDiseaseService internationalDiseaseService;

    public InternationalDiseaseResource(InternationalDiseaseService internationalDiseaseService) {
        super(InternationalDiseaseResource.class, "internationalDisease");
        this.internationalDiseaseService = internationalDiseaseService;
    }

    @PostMapping("/international-disease/search")
    @PreAuthorize(AccessConstants.DOCTOR)
    public ResponseEntity<List<InternationalDiseaseDTO>> search(
        Pageable pageable,
        @RequestBody SearchInternationalDiseaseDTO searchInternationalDiseaseDTO
    ) {
        Page<InternationalDiseaseDTO> page = internationalDiseaseService.search(pageable, searchInternationalDiseaseDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
