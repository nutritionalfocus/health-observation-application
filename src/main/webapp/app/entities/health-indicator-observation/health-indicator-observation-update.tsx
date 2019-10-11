import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './health-indicator-observation.reducer';
import { IHealthIndicatorObservation } from 'app/shared/model/health-indicator-observation.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHealthIndicatorObservationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IHealthIndicatorObservationUpdateState {
  isNew: boolean;
}

export class HealthIndicatorObservationUpdate extends React.Component<
  IHealthIndicatorObservationUpdateProps,
  IHealthIndicatorObservationUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.observationTime = convertDateTimeToServer(values.observationTime);
    values.receivedTime = convertDateTimeToServer(values.receivedTime);

    if (errors.length === 0) {
      const { healthIndicatorObservationEntity } = this.props;
      const entity = {
        ...healthIndicatorObservationEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/health-indicator-observation');
  };

  render() {
    const { healthIndicatorObservationEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="healthObservationApplicationApp.healthIndicatorObservation.home.createOrEditLabel">
              Create or edit a HealthIndicatorObservation
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : healthIndicatorObservationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="health-indicator-observation-id">ID</Label>
                    <AvInput id="health-indicator-observation-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="observationTimeLabel" for="health-indicator-observation-observationTime">
                    Observation Time
                  </Label>
                  <AvInput
                    id="health-indicator-observation-observationTime"
                    type="datetime-local"
                    className="form-control"
                    name="observationTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.healthIndicatorObservationEntity.observationTime)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="observationAccuracyLabel" for="health-indicator-observation-observationAccuracy">
                    Observation Accuracy
                  </Label>
                  <AvField
                    id="health-indicator-observation-observationAccuracy"
                    type="string"
                    className="form-control"
                    name="observationAccuracy"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="receivedTimeLabel" for="health-indicator-observation-receivedTime">
                    Received Time
                  </Label>
                  <AvInput
                    id="health-indicator-observation-receivedTime"
                    type="datetime-local"
                    className="form-control"
                    name="receivedTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.healthIndicatorObservationEntity.receivedTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorIdLabel" for="health-indicator-observation-indicatorId">
                    Indicator Id
                  </Label>
                  <AvField
                    id="health-indicator-observation-indicatorId"
                    type="string"
                    className="form-control"
                    name="indicatorId"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorTypeLabel" for="health-indicator-observation-indicatorType">
                    Indicator Type
                  </Label>
                  <AvInput
                    id="health-indicator-observation-indicatorType"
                    type="select"
                    className="form-control"
                    name="indicatorType"
                    value={(!isNew && healthIndicatorObservationEntity.indicatorType) || 'HEART_RATE_VARIABILITY'}
                  >
                    <option value="HEART_RATE_VARIABILITY">HEART_RATE_VARIABILITY</option>
                    <option value="SYSTOLIC_BLOOD_PRESSURE">SYSTOLIC_BLOOD_PRESSURE</option>
                    <option value="ASYSTOLIC_BLOOD_PRESSURE">ASYSTOLIC_BLOOD_PRESSURE</option>
                    <option value="VISERAL_FAT">VISERAL_FAT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorNameLabel" for="health-indicator-observation-indicatorName">
                    Indicator Name
                  </Label>
                  <AvField
                    id="health-indicator-observation-indicatorName"
                    type="text"
                    name="indicatorName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="attributedEntityTypeLabel" for="health-indicator-observation-attributedEntityType">
                    Attributed Entity Type
                  </Label>
                  <AvInput
                    id="health-indicator-observation-attributedEntityType"
                    type="select"
                    className="form-control"
                    name="attributedEntityType"
                    value={(!isNew && healthIndicatorObservationEntity.attributedEntityType) || 'PERSON'}
                  >
                    <option value="PERSON">PERSON</option>
                    <option value="ANIMAL">ANIMAL</option>
                    <option value="TEST">TEST</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="attributedEntityNameLabel" for="health-indicator-observation-attributedEntityName">
                    Attributed Entity Name
                  </Label>
                  <AvField
                    id="health-indicator-observation-attributedEntityName"
                    type="text"
                    name="attributedEntityName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorSourceTypeLabel" for="health-indicator-observation-indicatorSourceType">
                    Indicator Source Type
                  </Label>
                  <AvInput
                    id="health-indicator-observation-indicatorSourceType"
                    type="select"
                    className="form-control"
                    name="indicatorSourceType"
                    value={(!isNew && healthIndicatorObservationEntity.indicatorSourceType) || 'HEART_RATE_MONITOR'}
                  >
                    <option value="HEART_RATE_MONITOR">HEART_RATE_MONITOR</option>
                    <option value="SPORTS_WATCH">SPORTS_WATCH</option>
                    <option value="BLOOD_PRESSURE_CUFF">BLOOD_PRESSURE_CUFF</option>
                    <option value="FULL_BODY_IMAGING_DEVICE">FULL_BODY_IMAGING_DEVICE</option>
                    <option value="TANITA_MACHINE">TANITA_MACHINE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorSourceNameLabel" for="health-indicator-observation-indicatorSourceName">
                    Indicator Source Name
                  </Label>
                  <AvField
                    id="health-indicator-observation-indicatorSourceName"
                    type="text"
                    name="indicatorSourceName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="indicatorSourceLinkLabel" for="health-indicator-observation-indicatorSourceLink">
                    Indicator Source Link
                  </Label>
                  <AvField
                    id="health-indicator-observation-indicatorSourceLink"
                    type="text"
                    name="indicatorSourceLink"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/health-indicator-observation" replace color="info">
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
  healthIndicatorObservationEntity: storeState.healthIndicatorObservation.entity,
  loading: storeState.healthIndicatorObservation.loading,
  updating: storeState.healthIndicatorObservation.updating,
  updateSuccess: storeState.healthIndicatorObservation.updateSuccess
});

const mapDispatchToProps = {
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
)(HealthIndicatorObservationUpdate);
