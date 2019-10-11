package uk.co.nfl.service.impl;

import uk.co.nfl.service.HealthObservationService;
import uk.co.nfl.domain.HealthObservation;
import uk.co.nfl.repository.HealthObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link HealthObservation}.
 */
@Service
public class HealthObservationServiceImpl implements HealthObservationService {

    private final Logger log = LoggerFactory.getLogger(HealthObservationServiceImpl.class);

    private final HealthObservationRepository healthObservationRepository;

    public HealthObservationServiceImpl(HealthObservationRepository healthObservationRepository) {
        this.healthObservationRepository = healthObservationRepository;
    }

    /**
     * Save a healthObservation.
     *
     * @param healthObservation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HealthObservation save(HealthObservation healthObservation) {
        log.debug("Request to save HealthObservation : {}", healthObservation);
        return healthObservationRepository.save(healthObservation);
    }

    /**
     * Get all the healthObservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<HealthObservation> findAll(Pageable pageable) {
        log.debug("Request to get all HealthObservations");
        return healthObservationRepository.findAll(pageable);
    }

    /**
     * Get all the healthObservations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<HealthObservation> findAllWithEagerRelationships(Pageable pageable) {
        return healthObservationRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one healthObservation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<HealthObservation> findOne(String id) {
        log.debug("Request to get HealthObservation : {}", id);
        return healthObservationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the healthObservation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete HealthObservation : {}", id);
        healthObservationRepository.deleteById(id);
    }
}
