package uk.co.nfl.web.rest;

import uk.co.nfl.domain.HealthObservation;
import uk.co.nfl.service.HealthObservationService;
import uk.co.nfl.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uk.co.nfl.domain.HealthObservation}.
 */
@RestController
@RequestMapping("/api")
public class HealthObservationResource {

    private final Logger log = LoggerFactory.getLogger(HealthObservationResource.class);

    private static final String ENTITY_NAME = "healthObservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthObservationService healthObservationService;

    public HealthObservationResource(HealthObservationService healthObservationService) {
        this.healthObservationService = healthObservationService;
    }

    /**
     * {@code POST  /health-observations} : Create a new healthObservation.
     *
     * @param healthObservation the healthObservation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthObservation, or with status {@code 400 (Bad Request)} if the healthObservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-observations")
    public ResponseEntity<HealthObservation> createHealthObservation(@Valid @RequestBody HealthObservation healthObservation) throws URISyntaxException {
        log.debug("REST request to save HealthObservation : {}", healthObservation);
        if (healthObservation.getId() != null) {
            throw new BadRequestAlertException("A new healthObservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthObservation result = healthObservationService.save(healthObservation);
        return ResponseEntity.created(new URI("/api/health-observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-observations} : Updates an existing healthObservation.
     *
     * @param healthObservation the healthObservation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthObservation,
     * or with status {@code 400 (Bad Request)} if the healthObservation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthObservation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-observations")
    public ResponseEntity<HealthObservation> updateHealthObservation(@Valid @RequestBody HealthObservation healthObservation) throws URISyntaxException {
        log.debug("REST request to update HealthObservation : {}", healthObservation);
        if (healthObservation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthObservation result = healthObservationService.save(healthObservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, healthObservation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /health-observations} : get all the healthObservations.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthObservations in body.
     */
    @GetMapping("/health-observations")
    public ResponseEntity<List<HealthObservation>> getAllHealthObservations(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of HealthObservations");
        Page<HealthObservation> page;
        if (eagerload) {
            page = healthObservationService.findAllWithEagerRelationships(pageable);
        } else {
            page = healthObservationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /health-observations/:id} : get the "id" healthObservation.
     *
     * @param id the id of the healthObservation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthObservation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-observations/{id}")
    public ResponseEntity<HealthObservation> getHealthObservation(@PathVariable String id) {
        log.debug("REST request to get HealthObservation : {}", id);
        Optional<HealthObservation> healthObservation = healthObservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthObservation);
    }

    /**
     * {@code DELETE  /health-observations/:id} : delete the "id" healthObservation.
     *
     * @param id the id of the healthObservation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-observations/{id}")
    public ResponseEntity<Void> deleteHealthObservation(@PathVariable String id) {
        log.debug("REST request to delete HealthObservation : {}", id);
        healthObservationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
