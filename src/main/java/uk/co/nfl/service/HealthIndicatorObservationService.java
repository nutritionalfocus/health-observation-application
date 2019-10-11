package uk.co.nfl.service;

import uk.co.nfl.domain.HealthIndicatorObservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link HealthIndicatorObservation}.
 */
public interface HealthIndicatorObservationService {

    /**
     * Save a healthIndicatorObservation.
     *
     * @param healthIndicatorObservation the entity to save.
     * @return the persisted entity.
     */
    HealthIndicatorObservation save(HealthIndicatorObservation healthIndicatorObservation);

    /**
     * Get all the healthIndicatorObservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HealthIndicatorObservation> findAll(Pageable pageable);


    /**
     * Get the "id" healthIndicatorObservation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthIndicatorObservation> findOne(String id);

    /**
     * Delete the "id" healthIndicatorObservation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
