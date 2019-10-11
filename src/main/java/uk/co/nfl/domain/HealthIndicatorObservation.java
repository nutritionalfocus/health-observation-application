package uk.co.nfl.domain;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import uk.co.nfl.domain.enumeration.IndicatorType;

import uk.co.nfl.domain.enumeration.EntityType;

import uk.co.nfl.domain.enumeration.SourceType;

/**
 * Health Indicator Observation entity
 */
@ApiModel(description = "Health Indicator Observation entity")
@Document(collection = "health_indicator_observation")
public class HealthIndicatorObservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("observation_time")
    private Instant observationTime;

    @NotNull
    @Field("observation_accuracy")
    private Long observationAccuracy;

    @Field("received_time")
    private Instant receivedTime;

    @NotNull
    @Field("indicator_id")
    private Long indicatorId;

    @NotNull
    @Field("indicator_type")
    private IndicatorType indicatorType;

    @NotNull
    @Field("indicator_name")
    private String indicatorName;

    @NotNull
    @Field("attributed_entity_type")
    private EntityType attributedEntityType;

    @NotNull
    @Field("attributed_entity_name")
    private String attributedEntityName;

    @NotNull
    @Field("indicator_source_type")
    private SourceType indicatorSourceType;

    @NotNull
    @Field("indicator_source_name")
    private String indicatorSourceName;

    @NotNull
    @Field("indicator_source_link")
    private String indicatorSourceLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getObservationTime() {
        return observationTime;
    }

    public HealthIndicatorObservation observationTime(Instant observationTime) {
        this.observationTime = observationTime;
        return this;
    }

    public void setObservationTime(Instant observationTime) {
        this.observationTime = observationTime;
    }

    public Long getObservationAccuracy() {
        return observationAccuracy;
    }

    public HealthIndicatorObservation observationAccuracy(Long observationAccuracy) {
        this.observationAccuracy = observationAccuracy;
        return this;
    }

    public void setObservationAccuracy(Long observationAccuracy) {
        this.observationAccuracy = observationAccuracy;
    }

    public Instant getReceivedTime() {
        return receivedTime;
    }

    public HealthIndicatorObservation receivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }

    public void setReceivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Long getIndicatorId() {
        return indicatorId;
    }

    public HealthIndicatorObservation indicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
        return this;
    }

    public void setIndicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    public HealthIndicatorObservation indicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
        return this;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public HealthIndicatorObservation indicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
        return this;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public EntityType getAttributedEntityType() {
        return attributedEntityType;
    }

    public HealthIndicatorObservation attributedEntityType(EntityType attributedEntityType) {
        this.attributedEntityType = attributedEntityType;
        return this;
    }

    public void setAttributedEntityType(EntityType attributedEntityType) {
        this.attributedEntityType = attributedEntityType;
    }

    public String getAttributedEntityName() {
        return attributedEntityName;
    }

    public HealthIndicatorObservation attributedEntityName(String attributedEntityName) {
        this.attributedEntityName = attributedEntityName;
        return this;
    }

    public void setAttributedEntityName(String attributedEntityName) {
        this.attributedEntityName = attributedEntityName;
    }

    public SourceType getIndicatorSourceType() {
        return indicatorSourceType;
    }

    public HealthIndicatorObservation indicatorSourceType(SourceType indicatorSourceType) {
        this.indicatorSourceType = indicatorSourceType;
        return this;
    }

    public void setIndicatorSourceType(SourceType indicatorSourceType) {
        this.indicatorSourceType = indicatorSourceType;
    }

    public String getIndicatorSourceName() {
        return indicatorSourceName;
    }

    public HealthIndicatorObservation indicatorSourceName(String indicatorSourceName) {
        this.indicatorSourceName = indicatorSourceName;
        return this;
    }

    public void setIndicatorSourceName(String indicatorSourceName) {
        this.indicatorSourceName = indicatorSourceName;
    }

    public String getIndicatorSourceLink() {
        return indicatorSourceLink;
    }

    public HealthIndicatorObservation indicatorSourceLink(String indicatorSourceLink) {
        this.indicatorSourceLink = indicatorSourceLink;
        return this;
    }

    public void setIndicatorSourceLink(String indicatorSourceLink) {
        this.indicatorSourceLink = indicatorSourceLink;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HealthIndicatorObservation)) {
            return false;
        }
        return id != null && id.equals(((HealthIndicatorObservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HealthIndicatorObservation{" +
            "id=" + getId() +
            ", observationTime='" + getObservationTime() + "'" +
            ", observationAccuracy=" + getObservationAccuracy() +
            ", receivedTime='" + getReceivedTime() + "'" +
            ", indicatorId=" + getIndicatorId() +
            ", indicatorType='" + getIndicatorType() + "'" +
            ", indicatorName='" + getIndicatorName() + "'" +
            ", attributedEntityType='" + getAttributedEntityType() + "'" +
            ", attributedEntityName='" + getAttributedEntityName() + "'" +
            ", indicatorSourceType='" + getIndicatorSourceType() + "'" +
            ", indicatorSourceName='" + getIndicatorSourceName() + "'" +
            ", indicatorSourceLink='" + getIndicatorSourceLink() + "'" +
            "}";
    }
}
