import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHealthIndicatorObservation } from 'app/shared/model/health-indicator-observation.model';
import { getEntities as getHealthIndicatorObservations } from 'app/entities/health-indicator-observation/health-indicator-observation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './health-observation.reducer';
import { IHealthObservation } from 'app/shared/model/health-observation.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHealthObservationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IHealthObservationUpdateState {
  isNew: boolean;
  idshealthIndicatorObservations: any[];
}

export class HealthObservationUpdate extends React.Component<IHealthObservationUpdateProps, IHealthObservationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idshealthIndicatorObservations: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getHealthIndicatorObservations();
  }

  saveEntity = (event, errors, values) => {
    values.observationTime = convertDateTimeToServer(values.observationTime);

    if (errors.length === 0) {
      const { healthObservationEntity } = this.props;
      const entity = {
        ...healthObservationEntity,
        ...values,
        healthIndicatorObservations: mapIdList(values.healthIndicatorObservations)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/health-observation');
  };

  render() {
    const { healthObservationEntity, healthIndicatorObservations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="healthObservationApplicationApp.healthObservation.home.createOrEditLabel">Create or edit a HealthObservation</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : healthObservationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="health-observation-id">ID</Label>
                    <AvInput id="health-observation-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="descriptionLabel" for="health-observation-description">
                    Description
                  </Label>
                  <AvField
                    id="health-observation-description"
                    type="text"
                    name="description"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="confidenceLabel" for="health-observation-confidence">
                    Confidence
                  </Label>
                  <AvField
                    id="health-observation-confidence"
                    type="string"
                    className="form-control"
                    name="confidence"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="observationTimeLabel" for="health-observation-observationTime">
                    Observation Time
                  </Label>
                  <AvInput
                    id="health-observation-observationTime"
                    type="datetime-local"
                    className="form-control"
                    name="observationTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.healthObservationEntity.observationTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="observedByLabel" for="health-observation-observedBy">
                    Observed By
                  </Label>
                  <AvField
                    id="health-observation-observedBy"
                    type="text"
                    name="observedBy"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="health-observation-healthIndicatorObservations">Health Indicator Observations</Label>
                  <AvInput
                    id="health-observation-healthIndicatorObservations"
                    type="select"
                    multiple
                    className="form-control"
                    name="healthIndicatorObservations"
                    value={
                      healthObservationEntity.healthIndicatorObservations &&
                      healthObservationEntity.healthIndicatorObservations.map(e => e.id)
                    }
                  >
                    <option value="" key="0" />
                    {healthIndicatorObservations
                      ? healthIndicatorObservations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/health-observation" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  healthIndicatorObservations: storeState.healthIndicatorObservation.entities,
  healthObservationEntity: storeState.healthObservation.entity,
  loading: storeState.healthObservation.loading,
  updating: storeState.healthObservation.updating,
  updateSuccess: storeState.healthObservation.updateSuccess
});

const mapDispatchToProps = {
  getHealthIndicatorObservations,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HealthObservationUpdate);
