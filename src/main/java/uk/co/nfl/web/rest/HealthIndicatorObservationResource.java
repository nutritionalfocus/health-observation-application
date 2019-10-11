package uk.co.nfl.web.rest;

import uk.co.nfl.domain.HealthIndicatorObservation;
import uk.co.nfl.service.HealthIndicatorObservationService;
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
 * REST controller for managing {@link uk.co.nfl.domain.HealthIndicatorObservation}.
 */
@RestController
@RequestMapping("/api")
public class HealthIndicatorObservationResource {

    private final Logger log = LoggerFactory.getLogger(HealthIndicatorObservationResource.class);

    private static final String ENTITY_NAME = "healthIndicatorObservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthIndicatorObservationService healthIndicatorObservationService;

    public HealthIndicatorObservationResource(HealthIndicatorObservationService healthIndicatorObservationService) {
        this.healthIndicatorObservationService = healthIndicatorObservationService;
    }

    /**
     * {@code POST  /health-indicator-observations} : Create a new healthIndicatorObservation.
     *
     * @param healthIndicatorObservation the healthIndicatorObservation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthIndicatorObservation, or with status {@code 400 (Bad Request)} if the healthIndicatorObservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-indicator-observations")
    public ResponseEntity<HealthIndicatorObservation> createHealthIndicatorObservation(@Valid @RequestBody HealthIndicatorObservation healthIndicatorObservation) throws URISyntaxException {
        log.debug("REST request to save HealthIndicatorObservation : {}", healthIndicatorObservation);
        if (healthIndicatorObservation.getId() != null) {
            throw new BadRequestAlertException("A new healthIndicatorObservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthIndicatorObservation result = healthIndicatorObservationService.save(healthIndicatorObservation);
        return ResponseEntity.created(new URI("/api/health-indicator-observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-indicator-observations} : Updates an existing healthIndicatorObservation.
     *
     * @param healthIndicatorObservation the healthIndicatorObservation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthIndicatorObservation,
     * or with status {@code 400 (Bad Request)} if the healthIndicatorObservation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthIndicatorObservation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-indicator-observations")
    public ResponseEntity<HealthIndicatorObservation> updateHealthIndicatorObservation(@Valid @RequestBody HealthIndicatorObservation healthIndicatorObservation) throws URISyntaxException {
        log.debug("REST request to update HealthIndicatorObservation : {}", healthIndicatorObservation);
        if (healthIndicatorObservation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthIndicatorObservation result = healthIndicatorObservationService.save(healthIndicatorObservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, healthIndicatorObservation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /health-indicator-observations} : get all the healthIndicatorObservations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthIndicatorObservations in body.
     */
    @GetMapping("/health-indicator-observations")
    public ResponseEntity<List<HealthIndicatorObservation>> getAllHealthIndicatorObservations(Pageable pageable) {
        log.debug("REST request to get a page of HealthIndicatorObservations");
        Page<HealthIndicatorObservation> page = healthIndicatorObservationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /health-indicator-observations/:id} : get the "id" healthIndicatorObservation.
     *
     * @param id the id of the healthIndicatorObservation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthIndicatorObservation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-indicator-observations/{id}")
    public ResponseEntity<HealthIndicatorObservation> getHealthIndicatorObservation(@PathVariable String id) {
        log.debug("REST request to get HealthIndicatorObservation : {}", id);
        Optional<HealthIndicatorObservation> healthIndicatorObservation = healthIndicatorObservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthIndicatorObservation);
    }

    /**
     * {@code DELETE  /health-indicator-observations/:id} : delete the "id" healthIndicatorObservation.
     *
     * @param id the id of the healthIndicatorObservation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-indicator-observations/{id}")
    public ResponseEntity<Void> deleteHealthIndicatorObservation(@PathVariable String id) {
        log.debug("REST request to delete HealthIndicatorObservation : {}", id);
        healthIndicatorObservationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
