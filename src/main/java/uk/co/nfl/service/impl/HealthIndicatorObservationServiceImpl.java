package uk.co.nfl.service.impl;

import uk.co.nfl.service.HealthIndicatorObservationService;
import uk.co.nfl.domain.HealthIndicatorObservation;
import uk.co.nfl.repository.HealthIndicatorObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link HealthIndicatorObservation}.
 */
@Service
public class HealthIndicatorObservationServiceImpl implements HealthIndicatorObservationService {

    private final Logger log = LoggerFactory.getLogger(HealthIndicatorObservationServiceImpl.class);

    private final HealthIndicatorObservationRepository healthIndicatorObservationRepository;

    public HealthIndicatorObservationServiceImpl(HealthIndicatorObservationRepository healthIndicatorObservationRepository) {
        this.healthIndicatorObservationRepository = healthIndicatorObservationRepository;
    }

    /**
     * Save a healthIndicatorObservation.
     *
     * @param healthIndicatorObservation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HealthIndicatorObservation save(HealthIndicatorObservation healthIndicatorObservation) {
        log.debug("Request to save HealthIndicatorObservation : {}", healthIndicatorObservation);
        return healthIndicatorObservationRepository.save(healthIndicatorObservation);
    }

    /**
     * Get all the healthIndicatorObservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<HealthIndicatorObservation> findAll(Pageable pageable) {
        log.debug("Request to get all HealthIndicatorObservations");
        return healthIndicatorObservationRepository.findAll(pageable);
    }


    /**
     * Get one healthIndicatorObservation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<HealthIndicatorObservation> findOne(String id) {
        log.debug("Request to get HealthIndicatorObservation : {}", id);
        return healthIndicatorObservationRepository.findById(id);
    }

    /**
     * Delete the healthIndicatorObservation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete HealthIndicatorObservation : {}", id);
        healthIndicatorObservationRepository.deleteById(id);
    }
}
