package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.security.SecurityUtils;
import com.customsoftware.medicalservices.service.MedicalCertificateService;
import com.customsoftware.medicalservices.service.UserService;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import com.customsoftware.medicalservices.service.mapper.MedicalCertificateMapper;
import com.customsoftware.medicalservices.service.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalCertificate}.
 */
@Service
@Transactional
public class MedicalCertificateServiceImpl extends AbstractServiceImpl implements MedicalCertificateService {

    private final MedicalCertificateMapper medicalCertificateMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    private final MedicalCertificateRepository medicalCertificateRepository;

    public MedicalCertificateServiceImpl(
        MedicalCertificateMapper medicalCertificateMapper,
        UserMapper userMapper,
        UserService userService,
        MedicalCertificateRepository medicalCertificateRepository
    ) {
        super(MedicalCertificateServiceImpl.class);
        this.medicalCertificateMapper = medicalCertificateMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.medicalCertificateRepository = medicalCertificateRepository;
    }

    @Override
    public MedicalCertificateDTO save(MedicalCertificateDTO medicalCertificateDTO) {
        log.debug("Request to save MedicalCertificate : {}", medicalCertificateDTO);
        MedicalCertificate medicalCertificate = medicalCertificateMapper.toEntity(medicalCertificateDTO);
        medicalCertificate.setDoctor(
            userService.findOneByLogin(SecurityUtils.currentUserLogin()).orElseThrow(UserService.SUPPLIER_NOT_FOUND)
        );
        medicalCertificate.setPatient(userMapper.userDTOToUser(medicalCertificateDTO.getPatient()));
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
        log.debug("Request to delete MedicalCertificate : {}", id);
        medicalCertificateRepository.deleteById(id);
    }
}
