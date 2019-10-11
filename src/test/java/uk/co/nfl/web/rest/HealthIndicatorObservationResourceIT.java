package uk.co.nfl.web.rest;

import uk.co.nfl.HealthObservationApplicationApp;
import uk.co.nfl.domain.HealthIndicatorObservation;
import uk.co.nfl.repository.HealthIndicatorObservationRepository;
import uk.co.nfl.service.HealthIndicatorObservationService;
import uk.co.nfl.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static uk.co.nfl.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.co.nfl.domain.enumeration.IndicatorType;
import uk.co.nfl.domain.enumeration.EntityType;
import uk.co.nfl.domain.enumeration.SourceType;
/**
 * Integration tests for the {@link HealthIndicatorObservationResource} REST controller.
 */
@SpringBootTest(classes = HealthObservationApplicationApp.class)
public class HealthIndicatorObservationResourceIT {

    private static final Instant DEFAULT_OBSERVATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OBSERVATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_OBSERVATION_ACCURACY = 1L;
    private static final Long UPDATED_OBSERVATION_ACCURACY = 2L;

    private static final Instant DEFAULT_RECEIVED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_INDICATOR_ID = 1L;
    private static final Long UPDATED_INDICATOR_ID = 2L;

    private static final IndicatorType DEFAULT_INDICATOR_TYPE = IndicatorType.HEART_RATE_VARIABILITY;
    private static final IndicatorType UPDATED_INDICATOR_TYPE = IndicatorType.SYSTOLIC_BLOOD_PRESSURE;

    private static final String DEFAULT_INDICATOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INDICATOR_NAME = "BBBBBBBBBB";

    private static final EntityType DEFAULT_ATTRIBUTED_ENTITY_TYPE = EntityType.PERSON;
    private static final EntityType UPDATED_ATTRIBUTED_ENTITY_TYPE = EntityType.ANIMAL;

    private static final String DEFAULT_ATTRIBUTED_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTED_ENTITY_NAME = "BBBBBBBBBB";

    private static final SourceType DEFAULT_INDICATOR_SOURCE_TYPE = SourceType.HEART_RATE_MONITOR;
    private static final SourceType UPDATED_INDICATOR_SOURCE_TYPE = SourceType.SPORTS_WATCH;

    private static final String DEFAULT_INDICATOR_SOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INDICATOR_SOURCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATOR_SOURCE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_INDICATOR_SOURCE_LINK = "BBBBBBBBBB";

    @Autowired
    private HealthIndicatorObservationRepository healthIndicatorObservationRepository;

    @Autowired
    private HealthIndicatorObservationService healthIndicatorObservationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restHealthIndicatorObservationMockMvc;

    private HealthIndicatorObservation healthIndicatorObservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthIndicatorObservationResource healthIndicatorObservationResource = new HealthIndicatorObservationResource(healthIndicatorObservationService);
        this.restHealthIndicatorObservationMockMvc = MockMvcBuilders.standaloneSetup(healthIndicatorObservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthIndicatorObservation createEntity() {
        HealthIndicatorObservation healthIndicatorObservation = new HealthIndicatorObservation()
            .observationTime(DEFAULT_OBSERVATION_TIME)
            .observationAccuracy(DEFAULT_OBSERVATION_ACCURACY)
            .receivedTime(DEFAULT_RECEIVED_TIME)
            .indicatorId(DEFAULT_INDICATOR_ID)
            .indicatorType(DEFAULT_INDICATOR_TYPE)
            .indicatorName(DEFAULT_INDICATOR_NAME)
            .attributedEntityType(DEFAULT_ATTRIBUTED_ENTITY_TYPE)
            .attributedEntityName(DEFAULT_ATTRIBUTED_ENTITY_NAME)
            .indicatorSourceType(DEFAULT_INDICATOR_SOURCE_TYPE)
            .indicatorSourceName(DEFAULT_INDICATOR_SOURCE_NAME)
            .indicatorSourceLink(DEFAULT_INDICATOR_SOURCE_LINK);
        return healthIndicatorObservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthIndicatorObservation createUpdatedEntity() {
        HealthIndicatorObservation healthIndicatorObservation = new HealthIndicatorObservation()
            .observationTime(UPDATED_OBSERVATION_TIME)
            .observationAccuracy(UPDATED_OBSERVATION_ACCURACY)
            .receivedTime(UPDATED_RECEIVED_TIME)
            .indicatorId(UPDATED_INDICATOR_ID)
            .indicatorType(UPDATED_INDICATOR_TYPE)
            .indicatorName(UPDATED_INDICATOR_NAME)
            .attributedEntityType(UPDATED_ATTRIBUTED_ENTITY_TYPE)
            .attributedEntityName(UPDATED_ATTRIBUTED_ENTITY_NAME)
            .indicatorSourceType(UPDATED_INDICATOR_SOURCE_TYPE)
            .indicatorSourceName(UPDATED_INDICATOR_SOURCE_NAME)
            .indicatorSourceLink(UPDATED_INDICATOR_SOURCE_LINK);
        return healthIndicatorObservation;
    }

    @BeforeEach
    public void initTest() {
        healthIndicatorObservationRepository.deleteAll();
        healthIndicatorObservation = createEntity();
    }

    @Test
    public void createHealthIndicatorObservation() throws Exception {
        int databaseSizeBeforeCreate = healthIndicatorObservationRepository.findAll().size();

        // Create the HealthIndicatorObservation
        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isCreated());

        // Validate the HealthIndicatorObservation in the database
        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeCreate + 1);
        HealthIndicatorObservation testHealthIndicatorObservation = healthIndicatorObservationList.get(healthIndicatorObservationList.size() - 1);
        assertThat(testHealthIndicatorObservation.getObservationTime()).isEqualTo(DEFAULT_OBSERVATION_TIME);
        assertThat(testHealthIndicatorObservation.getObservationAccuracy()).isEqualTo(DEFAULT_OBSERVATION_ACCURACY);
        assertThat(testHealthIndicatorObservation.getReceivedTime()).isEqualTo(DEFAULT_RECEIVED_TIME);
        assertThat(testHealthIndicatorObservation.getIndicatorId()).isEqualTo(DEFAULT_INDICATOR_ID);
        assertThat(testHealthIndicatorObservation.getIndicatorType()).isEqualTo(DEFAULT_INDICATOR_TYPE);
        assertThat(testHealthIndicatorObservation.getIndicatorName()).isEqualTo(DEFAULT_INDICATOR_NAME);
        assertThat(testHealthIndicatorObservation.getAttributedEntityType()).isEqualTo(DEFAULT_ATTRIBUTED_ENTITY_TYPE);
        assertThat(testHealthIndicatorObservation.getAttributedEntityName()).isEqualTo(DEFAULT_ATTRIBUTED_ENTITY_NAME);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceType()).isEqualTo(DEFAULT_INDICATOR_SOURCE_TYPE);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceName()).isEqualTo(DEFAULT_INDICATOR_SOURCE_NAME);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceLink()).isEqualTo(DEFAULT_INDICATOR_SOURCE_LINK);
    }

    @Test
    public void createHealthIndicatorObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthIndicatorObservationRepository.findAll().size();

        // Create the HealthIndicatorObservation with an existing ID
        healthIndicatorObservation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        // Validate the HealthIndicatorObservation in the database
        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkObservationTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setObservationTime(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkObservationAccuracyIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setObservationAccuracy(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorId(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorType(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorName(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAttributedEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setAttributedEntityType(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAttributedEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setAttributedEntityName(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorSourceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorSourceType(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorSourceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorSourceName(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndicatorSourceLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthIndicatorObservationRepository.findAll().size();
        // set the field null
        healthIndicatorObservation.setIndicatorSourceLink(null);

        // Create the HealthIndicatorObservation, which fails.

        restHealthIndicatorObservationMockMvc.perform(post("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllHealthIndicatorObservations() throws Exception {
        // Initialize the database
        healthIndicatorObservationRepository.save(healthIndicatorObservation);

        // Get all the healthIndicatorObservationList
        restHealthIndicatorObservationMockMvc.perform(get("/api/health-indicator-observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthIndicatorObservation.getId())))
            .andExpect(jsonPath("$.[*].observationTime").value(hasItem(DEFAULT_OBSERVATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].observationAccuracy").value(hasItem(DEFAULT_OBSERVATION_ACCURACY.intValue())))
            .andExpect(jsonPath("$.[*].receivedTime").value(hasItem(DEFAULT_RECEIVED_TIME.toString())))
            .andExpect(jsonPath("$.[*].indicatorId").value(hasItem(DEFAULT_INDICATOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].indicatorType").value(hasItem(DEFAULT_INDICATOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].indicatorName").value(hasItem(DEFAULT_INDICATOR_NAME)))
            .andExpect(jsonPath("$.[*].attributedEntityType").value(hasItem(DEFAULT_ATTRIBUTED_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attributedEntityName").value(hasItem(DEFAULT_ATTRIBUTED_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].indicatorSourceType").value(hasItem(DEFAULT_INDICATOR_SOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].indicatorSourceName").value(hasItem(DEFAULT_INDICATOR_SOURCE_NAME)))
            .andExpect(jsonPath("$.[*].indicatorSourceLink").value(hasItem(DEFAULT_INDICATOR_SOURCE_LINK)));
    }
    
    @Test
    public void getHealthIndicatorObservation() throws Exception {
        // Initialize the database
        healthIndicatorObservationRepository.save(healthIndicatorObservation);

        // Get the healthIndicatorObservation
        restHealthIndicatorObservationMockMvc.perform(get("/api/health-indicator-observations/{id}", healthIndicatorObservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthIndicatorObservation.getId()))
            .andExpect(jsonPath("$.observationTime").value(DEFAULT_OBSERVATION_TIME.toString()))
            .andExpect(jsonPath("$.observationAccuracy").value(DEFAULT_OBSERVATION_ACCURACY.intValue()))
            .andExpect(jsonPath("$.receivedTime").value(DEFAULT_RECEIVED_TIME.toString()))
            .andExpect(jsonPath("$.indicatorId").value(DEFAULT_INDICATOR_ID.intValue()))
            .andExpect(jsonPath("$.indicatorType").value(DEFAULT_INDICATOR_TYPE.toString()))
            .andExpect(jsonPath("$.indicatorName").value(DEFAULT_INDICATOR_NAME))
            .andExpect(jsonPath("$.attributedEntityType").value(DEFAULT_ATTRIBUTED_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.attributedEntityName").value(DEFAULT_ATTRIBUTED_ENTITY_NAME))
            .andExpect(jsonPath("$.indicatorSourceType").value(DEFAULT_INDICATOR_SOURCE_TYPE.toString()))
            .andExpect(jsonPath("$.indicatorSourceName").value(DEFAULT_INDICATOR_SOURCE_NAME))
            .andExpect(jsonPath("$.indicatorSourceLink").value(DEFAULT_INDICATOR_SOURCE_LINK));
    }

    @Test
    public void getNonExistingHealthIndicatorObservation() throws Exception {
        // Get the healthIndicatorObservation
        restHealthIndicatorObservationMockMvc.perform(get("/api/health-indicator-observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHealthIndicatorObservation() throws Exception {
        // Initialize the database
        healthIndicatorObservationService.save(healthIndicatorObservation);

        int databaseSizeBeforeUpdate = healthIndicatorObservationRepository.findAll().size();

        // Update the healthIndicatorObservation
        HealthIndicatorObservation updatedHealthIndicatorObservation = healthIndicatorObservationRepository.findById(healthIndicatorObservation.getId()).get();
        updatedHealthIndicatorObservation
            .observationTime(UPDATED_OBSERVATION_TIME)
            .observationAccuracy(UPDATED_OBSERVATION_ACCURACY)
            .receivedTime(UPDATED_RECEIVED_TIME)
            .indicatorId(UPDATED_INDICATOR_ID)
            .indicatorType(UPDATED_INDICATOR_TYPE)
            .indicatorName(UPDATED_INDICATOR_NAME)
            .attributedEntityType(UPDATED_ATTRIBUTED_ENTITY_TYPE)
            .attributedEntityName(UPDATED_ATTRIBUTED_ENTITY_NAME)
            .indicatorSourceType(UPDATED_INDICATOR_SOURCE_TYPE)
            .indicatorSourceName(UPDATED_INDICATOR_SOURCE_NAME)
            .indicatorSourceLink(UPDATED_INDICATOR_SOURCE_LINK);

        restHealthIndicatorObservationMockMvc.perform(put("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthIndicatorObservation)))
            .andExpect(status().isOk());

        // Validate the HealthIndicatorObservation in the database
        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeUpdate);
        HealthIndicatorObservation testHealthIndicatorObservation = healthIndicatorObservationList.get(healthIndicatorObservationList.size() - 1);
        assertThat(testHealthIndicatorObservation.getObservationTime()).isEqualTo(UPDATED_OBSERVATION_TIME);
        assertThat(testHealthIndicatorObservation.getObservationAccuracy()).isEqualTo(UPDATED_OBSERVATION_ACCURACY);
        assertThat(testHealthIndicatorObservation.getReceivedTime()).isEqualTo(UPDATED_RECEIVED_TIME);
        assertThat(testHealthIndicatorObservation.getIndicatorId()).isEqualTo(UPDATED_INDICATOR_ID);
        assertThat(testHealthIndicatorObservation.getIndicatorType()).isEqualTo(UPDATED_INDICATOR_TYPE);
        assertThat(testHealthIndicatorObservation.getIndicatorName()).isEqualTo(UPDATED_INDICATOR_NAME);
        assertThat(testHealthIndicatorObservation.getAttributedEntityType()).isEqualTo(UPDATED_ATTRIBUTED_ENTITY_TYPE);
        assertThat(testHealthIndicatorObservation.getAttributedEntityName()).isEqualTo(UPDATED_ATTRIBUTED_ENTITY_NAME);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceType()).isEqualTo(UPDATED_INDICATOR_SOURCE_TYPE);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceName()).isEqualTo(UPDATED_INDICATOR_SOURCE_NAME);
        assertThat(testHealthIndicatorObservation.getIndicatorSourceLink()).isEqualTo(UPDATED_INDICATOR_SOURCE_LINK);
    }

    @Test
    public void updateNonExistingHealthIndicatorObservation() throws Exception {
        int databaseSizeBeforeUpdate = healthIndicatorObservationRepository.findAll().size();

        // Create the HealthIndicatorObservation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthIndicatorObservationMockMvc.perform(put("/api/health-indicator-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthIndicatorObservation)))
            .andExpect(status().isBadRequest());

        // Validate the HealthIndicatorObservation in the database
        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteHealthIndicatorObservation() throws Exception {
        // Initialize the database
        healthIndicatorObservationService.save(healthIndicatorObservation);

        int databaseSizeBeforeDelete = healthIndicatorObservationRepository.findAll().size();

        // Delete the healthIndicatorObservation
        restHealthIndicatorObservationMockMvc.perform(delete("/api/health-indicator-observations/{id}", healthIndicatorObservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HealthIndicatorObservation> healthIndicatorObservationList = healthIndicatorObservationRepository.findAll();
        assertThat(healthIndicatorObservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthIndicatorObservation.class);
        HealthIndicatorObservation healthIndicatorObservation1 = new HealthIndicatorObservation();
        healthIndicatorObservation1.setId("id1");
        HealthIndicatorObservation healthIndicatorObservation2 = new HealthIndicatorObservation();
        healthIndicatorObservation2.setId(healthIndicatorObservation1.getId());
        assertThat(healthIndicatorObservation1).isEqualTo(healthIndicatorObservation2);
        healthIndicatorObservation2.setId("id2");
        assertThat(healthIndicatorObservation1).isNotEqualTo(healthIndicatorObservation2);
        healthIndicatorObservation1.setId(null);
        assertThat(healthIndicatorObservation1).isNotEqualTo(healthIndicatorObservation2);
    }
}
