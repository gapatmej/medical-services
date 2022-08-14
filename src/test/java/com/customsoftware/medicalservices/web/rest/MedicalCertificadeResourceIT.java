package com.customsoftware.medicalservices.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.customsoftware.medicalservices.IntegrationTest;
import com.customsoftware.medicalservices.domain.MedicalCertificade;
import com.customsoftware.medicalservices.domain.enumeration.IdentificationType;
import com.customsoftware.medicalservices.repository.MedicalCertificadeRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MedicalCertificadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicalCertificadeResourceIT {

    private static final Instant DEFAULT_EMISSION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EMISSION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CLINIC_HISTORY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CLINIC_HISTORY_NUMBER = "BBBBBBBBBB";

    private static final IdentificationType DEFAULT_IDENTIFICATION_TYPE = IdentificationType.DNI;
    private static final IdentificationType UPDATED_IDENTIFICATION_TYPE = IdentificationType.PASSPORT;

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ATTENTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATTENTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_REST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REST_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_SYMPTOM = "AAAAAAAAAA";
    private static final String UPDATED_SYMPTOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medical-certificades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicalCertificadeRepository medicalCertificadeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalCertificadeMockMvc;

    private MedicalCertificade medicalCertificade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCertificade createEntity(EntityManager em) {
        MedicalCertificade medicalCertificade = new MedicalCertificade()
            .emissionDate(DEFAULT_EMISSION_DATE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .clinicHistoryNumber(DEFAULT_CLINIC_HISTORY_NUMBER)
            .identificationType(DEFAULT_IDENTIFICATION_TYPE)
            .identification(DEFAULT_IDENTIFICATION)
            .phone(DEFAULT_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .attentionDate(DEFAULT_ATTENTION_DATE)
            .diagnosis(DEFAULT_DIAGNOSIS)
            .restType(DEFAULT_REST_TYPE)
            .fromDate(DEFAULT_FROM_DATE)
            .untilDate(DEFAULT_UNTIL_DATE)
            .total(DEFAULT_TOTAL)
            .observation(DEFAULT_OBSERVATION)
            .symptom(DEFAULT_SYMPTOM);
        return medicalCertificade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCertificade createUpdatedEntity(EntityManager em) {
        MedicalCertificade medicalCertificade = new MedicalCertificade()
            .emissionDate(UPDATED_EMISSION_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .clinicHistoryNumber(UPDATED_CLINIC_HISTORY_NUMBER)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .identification(UPDATED_IDENTIFICATION)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .attentionDate(UPDATED_ATTENTION_DATE)
            .diagnosis(UPDATED_DIAGNOSIS)
            .restType(UPDATED_REST_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .total(UPDATED_TOTAL)
            .observation(UPDATED_OBSERVATION)
            .symptom(UPDATED_SYMPTOM);
        return medicalCertificade;
    }

    @BeforeEach
    public void initTest() {
        medicalCertificade = createEntity(em);
    }

    @Test
    @Transactional
    void createMedicalCertificade() throws Exception {
        int databaseSizeBeforeCreate = medicalCertificadeRepository.findAll().size();
        // Create the MedicalCertificade
        restMedicalCertificadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isCreated());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalCertificade testMedicalCertificade = medicalCertificadeList.get(medicalCertificadeList.size() - 1);
        assertThat(testMedicalCertificade.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testMedicalCertificade.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMedicalCertificade.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMedicalCertificade.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMedicalCertificade.getClinicHistoryNumber()).isEqualTo(DEFAULT_CLINIC_HISTORY_NUMBER);
        assertThat(testMedicalCertificade.getIdentificationType()).isEqualTo(DEFAULT_IDENTIFICATION_TYPE);
        assertThat(testMedicalCertificade.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testMedicalCertificade.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMedicalCertificade.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testMedicalCertificade.getAttentionDate()).isEqualTo(DEFAULT_ATTENTION_DATE);
        assertThat(testMedicalCertificade.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testMedicalCertificade.getRestType()).isEqualTo(DEFAULT_REST_TYPE);
        assertThat(testMedicalCertificade.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testMedicalCertificade.getUntilDate()).isEqualTo(DEFAULT_UNTIL_DATE);
        assertThat(testMedicalCertificade.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testMedicalCertificade.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testMedicalCertificade.getSymptom()).isEqualTo(DEFAULT_SYMPTOM);
    }

    @Test
    @Transactional
    void createMedicalCertificadeWithExistingId() throws Exception {
        // Create the MedicalCertificade with an existing ID
        medicalCertificade.setId(1L);

        int databaseSizeBeforeCreate = medicalCertificadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalCertificadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicalCertificades() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        // Get all the medicalCertificadeList
        restMedicalCertificadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCertificade.getId().intValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].clinicHistoryNumber").value(hasItem(DEFAULT_CLINIC_HISTORY_NUMBER)))
            .andExpect(jsonPath("$.[*].identificationType").value(hasItem(DEFAULT_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].attentionDate").value(hasItem(DEFAULT_ATTENTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].diagnosis").value(hasItem(DEFAULT_DIAGNOSIS)))
            .andExpect(jsonPath("$.[*].restType").value(hasItem(DEFAULT_REST_TYPE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].untilDate").value(hasItem(DEFAULT_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].symptom").value(hasItem(DEFAULT_SYMPTOM)));
    }

    @Test
    @Transactional
    void getMedicalCertificade() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        // Get the medicalCertificade
        restMedicalCertificadeMockMvc
            .perform(get(ENTITY_API_URL_ID, medicalCertificade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalCertificade.getId().intValue()))
            .andExpect(jsonPath("$.emissionDate").value(DEFAULT_EMISSION_DATE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.clinicHistoryNumber").value(DEFAULT_CLINIC_HISTORY_NUMBER))
            .andExpect(jsonPath("$.identificationType").value(DEFAULT_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.attentionDate").value(DEFAULT_ATTENTION_DATE.toString()))
            .andExpect(jsonPath("$.diagnosis").value(DEFAULT_DIAGNOSIS))
            .andExpect(jsonPath("$.restType").value(DEFAULT_REST_TYPE))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.untilDate").value(DEFAULT_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.symptom").value(DEFAULT_SYMPTOM));
    }

    @Test
    @Transactional
    void getNonExistingMedicalCertificade() throws Exception {
        // Get the medicalCertificade
        restMedicalCertificadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedicalCertificade() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();

        // Update the medicalCertificade
        MedicalCertificade updatedMedicalCertificade = medicalCertificadeRepository.findById(medicalCertificade.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalCertificade are not directly saved in db
        em.detach(updatedMedicalCertificade);
        updatedMedicalCertificade
            .emissionDate(UPDATED_EMISSION_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .clinicHistoryNumber(UPDATED_CLINIC_HISTORY_NUMBER)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .identification(UPDATED_IDENTIFICATION)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .attentionDate(UPDATED_ATTENTION_DATE)
            .diagnosis(UPDATED_DIAGNOSIS)
            .restType(UPDATED_REST_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .total(UPDATED_TOTAL)
            .observation(UPDATED_OBSERVATION)
            .symptom(UPDATED_SYMPTOM);

        restMedicalCertificadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedicalCertificade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMedicalCertificade))
            )
            .andExpect(status().isOk());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
        MedicalCertificade testMedicalCertificade = medicalCertificadeList.get(medicalCertificadeList.size() - 1);
        assertThat(testMedicalCertificade.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testMedicalCertificade.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMedicalCertificade.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMedicalCertificade.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMedicalCertificade.getClinicHistoryNumber()).isEqualTo(UPDATED_CLINIC_HISTORY_NUMBER);
        assertThat(testMedicalCertificade.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testMedicalCertificade.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testMedicalCertificade.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMedicalCertificade.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testMedicalCertificade.getAttentionDate()).isEqualTo(UPDATED_ATTENTION_DATE);
        assertThat(testMedicalCertificade.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testMedicalCertificade.getRestType()).isEqualTo(UPDATED_REST_TYPE);
        assertThat(testMedicalCertificade.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMedicalCertificade.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testMedicalCertificade.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testMedicalCertificade.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testMedicalCertificade.getSymptom()).isEqualTo(UPDATED_SYMPTOM);
    }

    @Test
    @Transactional
    void putNonExistingMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicalCertificade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicalCertificadeWithPatch() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();

        // Update the medicalCertificade using partial update
        MedicalCertificade partialUpdatedMedicalCertificade = new MedicalCertificade();
        partialUpdatedMedicalCertificade.setId(medicalCertificade.getId());

        partialUpdatedMedicalCertificade
            .firstName(UPDATED_FIRST_NAME)
            .clinicHistoryNumber(UPDATED_CLINIC_HISTORY_NUMBER)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .attentionDate(UPDATED_ATTENTION_DATE)
            .restType(UPDATED_REST_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .total(UPDATED_TOTAL)
            .observation(UPDATED_OBSERVATION);

        restMedicalCertificadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicalCertificade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicalCertificade))
            )
            .andExpect(status().isOk());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
        MedicalCertificade testMedicalCertificade = medicalCertificadeList.get(medicalCertificadeList.size() - 1);
        assertThat(testMedicalCertificade.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testMedicalCertificade.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMedicalCertificade.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMedicalCertificade.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMedicalCertificade.getClinicHistoryNumber()).isEqualTo(UPDATED_CLINIC_HISTORY_NUMBER);
        assertThat(testMedicalCertificade.getIdentificationType()).isEqualTo(DEFAULT_IDENTIFICATION_TYPE);
        assertThat(testMedicalCertificade.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testMedicalCertificade.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMedicalCertificade.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testMedicalCertificade.getAttentionDate()).isEqualTo(UPDATED_ATTENTION_DATE);
        assertThat(testMedicalCertificade.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testMedicalCertificade.getRestType()).isEqualTo(UPDATED_REST_TYPE);
        assertThat(testMedicalCertificade.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMedicalCertificade.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testMedicalCertificade.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testMedicalCertificade.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testMedicalCertificade.getSymptom()).isEqualTo(DEFAULT_SYMPTOM);
    }

    @Test
    @Transactional
    void fullUpdateMedicalCertificadeWithPatch() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();

        // Update the medicalCertificade using partial update
        MedicalCertificade partialUpdatedMedicalCertificade = new MedicalCertificade();
        partialUpdatedMedicalCertificade.setId(medicalCertificade.getId());

        partialUpdatedMedicalCertificade
            .emissionDate(UPDATED_EMISSION_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .clinicHistoryNumber(UPDATED_CLINIC_HISTORY_NUMBER)
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .identification(UPDATED_IDENTIFICATION)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .attentionDate(UPDATED_ATTENTION_DATE)
            .diagnosis(UPDATED_DIAGNOSIS)
            .restType(UPDATED_REST_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .total(UPDATED_TOTAL)
            .observation(UPDATED_OBSERVATION)
            .symptom(UPDATED_SYMPTOM);

        restMedicalCertificadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicalCertificade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicalCertificade))
            )
            .andExpect(status().isOk());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
        MedicalCertificade testMedicalCertificade = medicalCertificadeList.get(medicalCertificadeList.size() - 1);
        assertThat(testMedicalCertificade.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testMedicalCertificade.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMedicalCertificade.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMedicalCertificade.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMedicalCertificade.getClinicHistoryNumber()).isEqualTo(UPDATED_CLINIC_HISTORY_NUMBER);
        assertThat(testMedicalCertificade.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testMedicalCertificade.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testMedicalCertificade.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMedicalCertificade.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testMedicalCertificade.getAttentionDate()).isEqualTo(UPDATED_ATTENTION_DATE);
        assertThat(testMedicalCertificade.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testMedicalCertificade.getRestType()).isEqualTo(UPDATED_REST_TYPE);
        assertThat(testMedicalCertificade.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMedicalCertificade.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testMedicalCertificade.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testMedicalCertificade.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testMedicalCertificade.getSymptom()).isEqualTo(UPDATED_SYMPTOM);
    }

    @Test
    @Transactional
    void patchNonExistingMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicalCertificade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicalCertificade() throws Exception {
        int databaseSizeBeforeUpdate = medicalCertificadeRepository.findAll().size();
        medicalCertificade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicalCertificadeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicalCertificade))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicalCertificade in the database
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicalCertificade() throws Exception {
        // Initialize the database
        medicalCertificadeRepository.saveAndFlush(medicalCertificade);

        int databaseSizeBeforeDelete = medicalCertificadeRepository.findAll().size();

        // Delete the medicalCertificade
        restMedicalCertificadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicalCertificade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalCertificade> medicalCertificadeList = medicalCertificadeRepository.findAll();
        assertThat(medicalCertificadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
