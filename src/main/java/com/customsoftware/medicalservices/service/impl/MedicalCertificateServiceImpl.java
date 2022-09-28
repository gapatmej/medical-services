package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.domain.enumeration.MedicalCertificateStatus;
import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.security.SecurityUtils;
import com.customsoftware.medicalservices.service.*;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import com.customsoftware.medicalservices.service.mapper.MedicalCertificateMapper;
import com.customsoftware.medicalservices.service.mapper.ReportService;
import com.customsoftware.medicalservices.service.mapper.UserMapper;
import com.customsoftware.medicalservices.web.rest.errors.MedicalServicesRuntimeException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalCertificate}.
 */
@Service
@Transactional
public class MedicalCertificateServiceImpl extends AbstractServiceImpl implements MedicalCertificateService {

    public static final Supplier<RuntimeException> SUPPLIER_NOT_FOUND = () ->
        new MedicalServicesRuntimeException("Medical Certificate not Found");
    private final MedicalCertificateMapper medicalCertificateMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final MedicalCertificateRepository medicalCertificateRepository;
    private final ReportService reportService;
    private final SignService signService;
    private final MailService mailService;

    private final OrganizationService organizationService;

    public MedicalCertificateServiceImpl(
        MedicalCertificateMapper medicalCertificateMapper,
        UserMapper userMapper,
        UserService userService,
        MedicalCertificateRepository medicalCertificateRepository,
        ReportService reportService,
        SignService signService,
        MailService mailService,
        OrganizationService organizationService
    ) {
        super(MedicalCertificateServiceImpl.class);
        this.medicalCertificateMapper = medicalCertificateMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.medicalCertificateRepository = medicalCertificateRepository;
        this.reportService = reportService;
        this.signService = signService;
        this.mailService = mailService;
        this.organizationService = organizationService;
    }

    @Override
    public MedicalCertificateDTO save(MedicalCertificateDTO medicalCertificateDTO) {
        log.debug("Request to save MedicalCertificate : {}", medicalCertificateDTO);

        medicalCertificateRepository
            .searchById(medicalCertificateDTO.getId())
            .ifPresent(
                mC -> {
                    if (MedicalCertificateStatus.SIGNED.equals(mC.getStatus())) {
                        throw new MedicalServicesRuntimeException("Medical certificate signed");
                    }
                }
            );

        MedicalCertificate medicalCertificate = medicalCertificateMapper.toEntity(medicalCertificateDTO);
        medicalCertificate.setDoctor(userService.currentUserLogin());
        medicalCertificate.setPatient(userMapper.userDTOToUser(medicalCertificateDTO.getPatient()));
        medicalCertificate.setStatus(MedicalCertificateStatus.CREATED);
        medicalCertificate = medicalCertificateRepository.save(medicalCertificate);

        return medicalCertificateMapper.toDto(medicalCertificate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCertificateDTO> search(Pageable pageable, SearchMedicalCertificateDTO searchMedicalCertificateDTO) {
        log.debug("Request to get all MedicalCertificates");
        List<MedicalCertificateDTO> medicalCertificateDTOList = new ArrayList<>();
        Page<MedicalCertificate> medicalCertificatePage = medicalCertificateRepository.search(searchMedicalCertificateDTO, pageable);
        medicalCertificatePage.forEach(
            mC -> {
                MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(mC);
                medicalCertificateDTO.setDoctor(userMapper.toDTOBasicData(mC.getDoctor()));
                medicalCertificateDTO.setPatient(userMapper.toDTOBasicData(mC.getPatient()));
                medicalCertificateDTOList.add(medicalCertificateDTO);
            }
        );
        return new PageImpl<>(medicalCertificateDTOList, medicalCertificatePage.getPageable(), medicalCertificatePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalCertificateDTO> searchById(Long id) {
        log.debug("Request to get MedicalCertificate : {}", id);
        return medicalCertificateRepository
            .searchById(id)
            .map(
                mC -> {
                    MedicalCertificateDTO medicalCertificateDTO = medicalCertificateMapper.toDto(mC);
                    medicalCertificateDTO.setDoctor(userMapper.toDTOBasicData(mC.getDoctor()));
                    medicalCertificateDTO.setPatient(userMapper.toDTOBasicData(mC.getPatient()));
                    return medicalCertificateDTO;
                }
            );
    }

    @Override
    public void delete(Long id) {
        medicalCertificateRepository
            .searchById(id)
            .ifPresent(
                mC -> {
                    if (MedicalCertificateStatus.SIGNED.equals(mC.getStatus())) {
                        throw new MedicalServicesRuntimeException("Medical certificate signed");
                    }
                    medicalCertificateRepository.deleteById(id);
                }
            );
    }

    @Override
    public void sign(Long id) {
        medicalCertificateRepository
            .searchByIdAndDoctor(id, SecurityUtils.currentUserLogin())
            .ifPresent(
                mC -> {
                    if (MedicalCertificateStatus.SIGNED.equals(mC.getStatus())) {
                        throw new MedicalServicesRuntimeException("Medical certificate signed");
                    }
                    Organization organization = organizationService.getOrganization();
                    try {
                        reportService.generateMedicalCertificate(organization, mC);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        throw new MedicalServicesRuntimeException(e.getMessage());
                    }
                    signService.sign(ServiceUtils.getMedicalCertificatePath(mC), mC.getDoctor());
                    mC.setStatus(MedicalCertificateStatus.SIGNED);
                    medicalCertificateRepository.save(mC);
                    sendMedicalCertificate(mC);
                }
            );
    }

    private void sendMedicalCertificate(MedicalCertificate medicalCertificate) {
        File medicalCertificateDoc = new File(ServiceUtils.getMedicalCertificatePath(medicalCertificate));
        mailService.sendEmailMedicalCertificate(medicalCertificate.getPatient(), medicalCertificateDoc);
    }

    @Override
    public ResponseEntity<InputStreamResource> getSignedCertificate(Long id) {
        MedicalCertificate medicalCertificate = medicalCertificateRepository
            .searchByIdAndDoctorOrPatient(id, SecurityUtils.currentUserLogin())
            .orElseThrow(SUPPLIER_NOT_FOUND);

        if (!MedicalCertificateStatus.SIGNED.equals(medicalCertificate.getStatus())) {
            throw new MedicalServicesRuntimeException("Medical certificate not signed yet");
        }

        InputStream in = null;
        try {
            in = new FileInputStream(ServiceUtils.getMedicalCertificatePath(medicalCertificate));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            throw new MedicalServicesRuntimeException("File not founded");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.add("Content-Disposition", "attachment; filename=" + ServiceUtils.getMedicalCertificateName(medicalCertificate));
        httpHeaders.add("filename", ServiceUtils.getMedicalCertificateName(medicalCertificate));

        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(in));
    }

    @Override
    public String resend(Long id) {
        MedicalCertificate medicalCertificate = medicalCertificateRepository
            .searchByIdAndDoctorOrPatient(id, SecurityUtils.currentUserLogin())
            .orElseThrow(SUPPLIER_NOT_FOUND);

        if (!MedicalCertificateStatus.SIGNED.equals(medicalCertificate.getStatus())) {
            throw new MedicalServicesRuntimeException("Medical certificate not signed yet");
        }

        sendMedicalCertificate(medicalCertificate);
        return medicalCertificate.getPatient().getEmail();
    }
}
