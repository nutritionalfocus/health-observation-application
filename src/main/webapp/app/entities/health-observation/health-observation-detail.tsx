import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './health-observation.reducer';
import { IHealthObservation } from 'app/shared/model/health-observation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHealthObservationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HealthObservationDetail extends React.Component<IHealthObservationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { healthObservationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            HealthObservation [<b>{healthObservationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{healthObservationEntity.description}</dd>
            <dt>
              <span id="confidence">Confidence</span>
            </dt>
            <dd>{healthObservationEntity.confidence}</dd>
            <dt>
              <span id="observationTime">Observation Time</span>
            </dt>
            <dd>
              <TextFormat value={healthObservationEntity.observationTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="observedBy">Observed By</span>
            </dt>
            <dd>{healthObservationEntity.observedBy}</dd>
            <dt>Health Indicator Observations</dt>
            <dd>
              {healthObservationEntity.healthIndicatorObservations
                ? healthObservationEntity.healthIndicatorObservations.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === healthObservationEntity.healthIndicatorObservations.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/health-observation" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/health-observation/${healthObservationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ healthObservation }: IRootState) => ({
  healthObservationEntity: healthObservation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HealthObservationDetail);
