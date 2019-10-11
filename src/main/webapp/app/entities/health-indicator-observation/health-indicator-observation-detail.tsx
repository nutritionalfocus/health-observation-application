import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './health-indicator-observation.reducer';
import { IHealthIndicatorObservation } from 'app/shared/model/health-indicator-observation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHealthIndicatorObservationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HealthIndicatorObservationDetail extends React.Component<IHealthIndicatorObservationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { healthIndicatorObservationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            HealthIndicatorObservation [<b>{healthIndicatorObservationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="observationTime">Observation Time</span>
            </dt>
            <dd>
              <TextFormat value={healthIndicatorObservationEntity.observationTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="observationAccuracy">Observation Accuracy</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.observationAccuracy}</dd>
            <dt>
              <span id="receivedTime">Received Time</span>
            </dt>
            <dd>
              <TextFormat value={healthIndicatorObservationEntity.receivedTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="indicatorId">Indicator Id</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorId}</dd>
            <dt>
              <span id="indicatorType">Indicator Type</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorType}</dd>
            <dt>
              <span id="indicatorName">Indicator Name</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorName}</dd>
            <dt>
              <span id="attributedEntityType">Attributed Entity Type</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.attributedEntityType}</dd>
            <dt>
              <span id="attributedEntityName">Attributed Entity Name</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.attributedEntityName}</dd>
            <dt>
              <span id="indicatorSourceType">Indicator Source Type</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorSourceType}</dd>
            <dt>
              <span id="indicatorSourceName">Indicator Source Name</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorSourceName}</dd>
            <dt>
              <span id="indicatorSourceLink">Indicator Source Link</span>
            </dt>
            <dd>{healthIndicatorObservationEntity.indicatorSourceLink}</dd>
          </dl>
          <Button tag={Link} to="/entity/health-indicator-observation" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button
            tag={Link}
            to={`/entity/health-indicator-observation/${healthIndicatorObservationEntity.id}/edit`}
            replace
            color="primary"
          >
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ healthIndicatorObservation }: IRootState) => ({
  healthIndicatorObservationEntity: healthIndicatorObservation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HealthIndicatorObservationDetail);
