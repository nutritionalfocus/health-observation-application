import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './health-indicator-observation.reducer';
import { IHealthIndicatorObservation } from 'app/shared/model/health-indicator-observation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IHealthIndicatorObservationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IHealthIndicatorObservationState = IPaginationBaseState;

export class HealthIndicatorObservation extends React.Component<IHealthIndicatorObservationProps, IHealthIndicatorObservationState> {
  state: IHealthIndicatorObservationState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { healthIndicatorObservationList, match } = this.props;
    return (
      <div>
        <h2 id="health-indicator-observation-heading">
          Health Indicator Observations
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Health Indicator Observation
          </Link>
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {healthIndicatorObservationList && healthIndicatorObservationList.length > 0 ? (
              <Table responsive aria-describedby="health-indicator-observation-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      ID <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('observationTime')}>
                      Observation Time <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('observationAccuracy')}>
                      Observation Accuracy <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('receivedTime')}>
                      Received Time <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorId')}>
                      Indicator Id <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorType')}>
                      Indicator Type <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorName')}>
                      Indicator Name <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('attributedEntityType')}>
                      Attributed Entity Type <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('attributedEntityName')}>
                      Attributed Entity Name <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorSourceType')}>
                      Indicator Source Type <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorSourceName')}>
                      Indicator Source Name <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('indicatorSourceLink')}>
                      Indicator Source Link <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {healthIndicatorObservationList.map((healthIndicatorObservation, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${healthIndicatorObservation.id}`} color="link" size="sm">
                          {healthIndicatorObservation.id}
                        </Button>
                      </td>
                      <td>
                        <TextFormat type="date" value={healthIndicatorObservation.observationTime} format={APP_DATE_FORMAT} />
                      </td>
                      <td>{healthIndicatorObservation.observationAccuracy}</td>
                      <td>
                        <TextFormat type="date" value={healthIndicatorObservation.receivedTime} format={APP_DATE_FORMAT} />
                      </td>
                      <td>{healthIndicatorObservation.indicatorId}</td>
                      <td>{healthIndicatorObservation.indicatorType}</td>
                      <td>{healthIndicatorObservation.indicatorName}</td>
                      <td>{healthIndicatorObservation.attributedEntityType}</td>
                      <td>{healthIndicatorObservation.attributedEntityName}</td>
                      <td>{healthIndicatorObservation.indicatorSourceType}</td>
                      <td>{healthIndicatorObservation.indicatorSourceName}</td>
                      <td>{healthIndicatorObservation.indicatorSourceLink}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${healthIndicatorObservation.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${healthIndicatorObservation.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${healthIndicatorObservation.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">No Health Indicator Observations found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ healthIndicatorObservation }: IRootState) => ({
  healthIndicatorObservationList: healthIndicatorObservation.entities,
  totalItems: healthIndicatorObservation.totalItems,
  links: healthIndicatorObservation.links,
  entity: healthIndicatorObservation.entity,
  updateSuccess: healthIndicatorObservation.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HealthIndicatorObservation);
