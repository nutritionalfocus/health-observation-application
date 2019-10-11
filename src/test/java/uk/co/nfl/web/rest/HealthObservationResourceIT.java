package uk.co.nfl.web.rest;

import uk.co.nfl.HealthObservationApplicationApp;
import uk.co.nfl.domain.HealthObservation;
import uk.co.nfl.repository.HealthObservationRepository;
import uk.co.nfl.service.HealthObservationService;
import uk.co.nfl.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static uk.co.nfl.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HealthObservationResource} REST controller.
 */
@SpringBootTest(classes = HealthObservationApplicationApp.class)
public class HealthObservationResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CONFIDENCE = 1L;
    private static final Long UPDATED_CONFIDENCE = 2L;

    private static final Instant DEFAULT_OBSERVATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OBSERVATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVED_BY = "BBBBBBBBBB";

    @Autowired
    private HealthObservationRepository healthObservationRepository;

    @Mock
    private HealthObservationRepository healthObservationRepositoryMock;

    @Mock
    private HealthObservationService healthObservationServiceMock;

    @Autowired
    private HealthObservationService healthObservationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restHealthObservationMockMvc;

    private HealthObservation healthObservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthObservationResource healthObservationResource = new HealthObservationResource(healthObservationService);
        this.restHealthObservationMockMvc = MockMvcBuilders.standaloneSetup(healthObservationResource)
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
    public static HealthObservation createEntity() {
        HealthObservation healthObservation = new HealthObservation()
            .description(DEFAULT_DESCRIPTION)
            .confidence(DEFAULT_CONFIDENCE)
            .observationTime(DEFAULT_OBSERVATION_TIME)
            .observedBy(DEFAULT_OBSERVED_BY);
        return healthObservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthObservation createUpdatedEntity() {
        HealthObservation healthObservation = new HealthObservation()
            .description(UPDATED_DESCRIPTION)
            .confidence(UPDATED_CONFIDENCE)
            .observationTime(UPDATED_OBSERVATION_TIME)
            .observedBy(UPDATED_OBSERVED_BY);
        return healthObservation;
    }

    @BeforeEach
    public void initTest() {
        healthObservationRepository.deleteAll();
        healthObservation = createEntity();
    }

    @Test
    public void createHealthObservation() throws Exception {
        int databaseSizeBeforeCreate = healthObservationRepository.findAll().size();

        // Create the HealthObservation
        restHealthObservationMockMvc.perform(post("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isCreated());

        // Validate the HealthObservation in the database
        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeCreate + 1);
        HealthObservation testHealthObservation = healthObservationList.get(healthObservationList.size() - 1);
        assertThat(testHealthObservation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHealthObservation.getConfidence()).isEqualTo(DEFAULT_CONFIDENCE);
        assertThat(testHealthObservation.getObservationTime()).isEqualTo(DEFAULT_OBSERVATION_TIME);
        assertThat(testHealthObservation.getObservedBy()).isEqualTo(DEFAULT_OBSERVED_BY);
    }

    @Test
    public void createHealthObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthObservationRepository.findAll().size();

        // Create the HealthObservation with an existing ID
        healthObservation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthObservationMockMvc.perform(post("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isBadRequest());

        // Validate the HealthObservation in the database
        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthObservationRepository.findAll().size();
        // set the field null
        healthObservation.setDescription(null);

        // Create the HealthObservation, which fails.

        restHealthObservationMockMvc.perform(post("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isBadRequest());

        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkConfidenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthObservationRepository.findAll().size();
        // set the field null
        healthObservation.setConfidence(null);

        // Create the HealthObservation, which fails.

        restHealthObservationMockMvc.perform(post("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isBadRequest());

        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkObservedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthObservationRepository.findAll().size();
        // set the field null
        healthObservation.setObservedBy(null);

        // Create the HealthObservation, which fails.

        restHealthObservationMockMvc.perform(post("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isBadRequest());

        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllHealthObservations() throws Exception {
        // Initialize the database
        healthObservationRepository.save(healthObservation);

        // Get all the healthObservationList
        restHealthObservationMockMvc.perform(get("/api/health-observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthObservation.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].confidence").value(hasItem(DEFAULT_CONFIDENCE.intValue())))
            .andExpect(jsonPath("$.[*].observationTime").value(hasItem(DEFAULT_OBSERVATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].observedBy").value(hasItem(DEFAULT_OBSERVED_BY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllHealthObservationsWithEagerRelationshipsIsEnabled() throws Exception {
        HealthObservationResource healthObservationResource = new HealthObservationResource(healthObservationServiceMock);
        when(healthObservationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restHealthObservationMockMvc = MockMvcBuilders.standaloneSetup(healthObservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restHealthObservationMockMvc.perform(get("/api/health-observations?eagerload=true"))
        .andExpect(status().isOk());

        verify(healthObservationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllHealthObservationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        HealthObservationResource healthObservationResource = new HealthObservationResource(healthObservationServiceMock);
            when(healthObservationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restHealthObservationMockMvc = MockMvcBuilders.standaloneSetup(healthObservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restHealthObservationMockMvc.perform(get("/api/health-observations?eagerload=true"))
        .andExpect(status().isOk());

            verify(healthObservationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getHealthObservation() throws Exception {
        // Initialize the database
        healthObservationRepository.save(healthObservation);

        // Get the healthObservation
        restHealthObservationMockMvc.perform(get("/api/health-observations/{id}", healthObservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthObservation.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.confidence").value(DEFAULT_CONFIDENCE.intValue()))
            .andExpect(jsonPath("$.observationTime").value(DEFAULT_OBSERVATION_TIME.toString()))
            .andExpect(jsonPath("$.observedBy").value(DEFAULT_OBSERVED_BY));
    }

    @Test
    public void getNonExistingHealthObservation() throws Exception {
        // Get the healthObservation
        restHealthObservationMockMvc.perform(get("/api/health-observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHealthObservation() throws Exception {
        // Initialize the database
        healthObservationService.save(healthObservation);

        int databaseSizeBeforeUpdate = healthObservationRepository.findAll().size();

        // Update the healthObservation
        HealthObservation updatedHealthObservation = healthObservationRepository.findById(healthObservation.getId()).get();
        updatedHealthObservation
            .description(UPDATED_DESCRIPTION)
            .confidence(UPDATED_CONFIDENCE)
            .observationTime(UPDATED_OBSERVATION_TIME)
            .observedBy(UPDATED_OBSERVED_BY);

        restHealthObservationMockMvc.perform(put("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthObservation)))
            .andExpect(status().isOk());

        // Validate the HealthObservation in the database
        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeUpdate);
        HealthObservation testHealthObservation = healthObservationList.get(healthObservationList.size() - 1);
        assertThat(testHealthObservation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHealthObservation.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
        assertThat(testHealthObservation.getObservationTime()).isEqualTo(UPDATED_OBSERVATION_TIME);
        assertThat(testHealthObservation.getObservedBy()).isEqualTo(UPDATED_OBSERVED_BY);
    }

    @Test
    public void updateNonExistingHealthObservation() throws Exception {
        int databaseSizeBeforeUpdate = healthObservationRepository.findAll().size();

        // Create the HealthObservation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthObservationMockMvc.perform(put("/api/health-observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthObservation)))
            .andExpect(status().isBadRequest());

        // Validate the HealthObservation in the database
        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteHealthObservation() throws Exception {
        // Initialize the database
        healthObservationService.save(healthObservation);

        int databaseSizeBeforeDelete = healthObservationRepository.findAll().size();

        // Delete the healthObservation
        restHealthObservationMockMvc.perform(delete("/api/health-observations/{id}", healthObservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HealthObservation> healthObservationList = healthObservationRepository.findAll();
        assertThat(healthObservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthObservation.class);
        HealthObservation healthObservation1 = new HealthObservation();
        healthObservation1.setId("id1");
        HealthObservation healthObservation2 = new HealthObservation();
        healthObservation2.setId(healthObservation1.getId());
        assertThat(healthObservation1).isEqualTo(healthObservation2);
        healthObservation2.setId("id2");
        assertThat(healthObservation1).isNotEqualTo(healthObservation2);
        healthObservation1.setId(null);
        assertThat(healthObservation1).isNotEqualTo(healthObservation2);
    }
}
