import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './health-observation.reducer';
import { IHealthObservation } from 'app/shared/model/health-observation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IHealthObservationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IHealthObservationState = IPaginationBaseState;

export class HealthObservation extends React.Component<IHealthObservationProps, IHealthObservationState> {
  state: IHealthObservationState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { healthObservationList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="health-observation-heading">
          Health Observations
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Health Observation
          </Link>
        </h2>
        <div className="table-responsive">
          {healthObservationList && healthObservationList.length > 0 ? (
            <Table responsive aria-describedby="health-observation-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('description')}>
                    Description <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('confidence')}>
                    Confidence <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('observationTime')}>
                    Observation Time <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('observedBy')}>
                    Observed By <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {healthObservationList.map((healthObservation, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${healthObservation.id}`} color="link" size="sm">
                        {healthObservation.id}
                      </Button>
                    </td>
                    <td>{healthObservation.description}</td>
                    <td>{healthObservation.confidence}</td>
                    <td>
                      <TextFormat type="date" value={healthObservation.observationTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{healthObservation.observedBy}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${healthObservation.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${healthObservation.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${healthObservation.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Health Observations found</div>
          )}
        </div>
        <div className={healthObservationList && healthObservationList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ healthObservation }: IRootState) => ({
  healthObservationList: healthObservation.entities,
  totalItems: healthObservation.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HealthObservation);
