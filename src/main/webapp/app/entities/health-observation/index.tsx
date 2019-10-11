import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HealthObservation from './health-observation';
import HealthObservationDetail from './health-observation-detail';
import HealthObservationUpdate from './health-observation-update';
import HealthObservationDeleteDialog from './health-observation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HealthObservationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HealthObservationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HealthObservationDetail} />
      <ErrorBoundaryRoute path={match.url} component={HealthObservation} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={HealthObservationDeleteDialog} />
  </>
);

export default Routes;
