package uk.co.nfl.service;

import uk.co.nfl.domain.HealthObservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link HealthObservation}.
 */
public interface HealthObservationService {

    /**
     * Save a healthObservation.
     *
     * @param healthObservation the entity to save.
     * @return the persisted entity.
     */
    HealthObservation save(HealthObservation healthObservation);

    /**
     * Get all the healthObservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HealthObservation> findAll(Pageable pageable);

    /**
     * Get all the healthObservations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<HealthObservation> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" healthObservation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthObservation> findOne(String id);

    /**
     * Delete the "id" healthObservation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
