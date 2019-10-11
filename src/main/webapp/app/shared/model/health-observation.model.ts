import { Moment } from 'moment';
import { IHealthIndicatorObservation } from 'app/shared/model/health-indicator-observation.model';

export interface IHealthObservation {
  id?: string;
  description?: string;
  confidence?: number;
  observationTime?: Moment;
  observedBy?: string;
  healthIndicatorObservations?: IHealthIndicatorObservation[];
}

export const defaultValue: Readonly<IHealthObservation> = {};
