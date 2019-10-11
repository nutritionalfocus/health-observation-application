import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HealthIndicatorObservation from './health-indicator-observation';
import HealthIndicatorObservationDetail from './health-indicator-observation-detail';
import HealthIndicatorObservationUpdate from './health-indicator-observation-update';
import HealthIndicatorObservationDeleteDialog from './health-indicator-observation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HealthIndicatorObservationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HealthIndicatorObservationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HealthIndicatorObservationDetail} />
      <ErrorBoundaryRoute path={match.url} component={HealthIndicatorObservation} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={HealthIndicatorObservationDeleteDialog} />
  </>
);

export default Routes;
