package uk.co.nfl.domain;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Health Observation entity
 */
@ApiModel(description = "Health Observation entity")
@Document(collection = "health_observation")
public class HealthObservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("confidence")
    private Long confidence;

    @Field("observation_time")
    private Instant observationTime;

    @NotNull
    @Field("observed_by")
    private String observedBy;

    @DBRef
    @Field("healthIndicatorObservations")
    private Set<HealthIndicatorObservation> healthIndicatorObservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public HealthObservation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getConfidence() {
        return confidence;
    }

    public HealthObservation confidence(Long confidence) {
        this.confidence = confidence;
        return this;
    }

    public void setConfidence(Long confidence) {
        this.confidence = confidence;
    }

    public Instant getObservationTime() {
        return observationTime;
    }

    public HealthObservation observationTime(Instant observationTime) {
        this.observationTime = observationTime;
        return this;
    }

    public void setObservationTime(Instant observationTime) {
        this.observationTime = observationTime;
    }

    public String getObservedBy() {
        return observedBy;
    }

    public HealthObservation observedBy(String observedBy) {
        this.observedBy = observedBy;
        return this;
    }

    public void setObservedBy(String observedBy) {
        this.observedBy = observedBy;
    }

    public Set<HealthIndicatorObservation> getHealthIndicatorObservations() {
        return healthIndicatorObservations;
    }

    public HealthObservation healthIndicatorObservations(Set<HealthIndicatorObservation> healthIndicatorObservations) {
        this.healthIndicatorObservations = healthIndicatorObservations;
        return this;
    }

    public HealthObservation addHealthIndicatorObservations(HealthIndicatorObservation healthIndicatorObservation) {
        this.healthIndicatorObservations.add(healthIndicatorObservation);
        healthIndicatorObservation.getHealthObservations().add(this);
        return this;
    }

    public HealthObservation removeHealthIndicatorObservations(HealthIndicatorObservation healthIndicatorObservation) {
        this.healthIndicatorObservations.remove(healthIndicatorObservation);
        healthIndicatorObservation.getHealthObservations().remove(this);
        return this;
    }

    public void setHealthIndicatorObservations(Set<HealthIndicatorObservation> healthIndicatorObservations) {
        this.healthIndicatorObservations = healthIndicatorObservations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HealthObservation)) {
            return false;
        }
        return id != null && id.equals(((HealthObservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HealthObservation{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", confidence=" + getConfidence() +
            ", observationTime='" + getObservationTime() + "'" +
            ", observedBy='" + getObservedBy() + "'" +
            "}";
    }
}
