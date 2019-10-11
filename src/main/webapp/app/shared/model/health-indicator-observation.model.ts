import { Moment } from 'moment';
import { IndicatorType } from 'app/shared/model/enumerations/indicator-type.model';
import { EntityType } from 'app/shared/model/enumerations/entity-type.model';
import { SourceType } from 'app/shared/model/enumerations/source-type.model';

export interface IHealthIndicatorObservation {
  id?: string;
  observationTime?: Moment;
  observationAccuracy?: number;
  receivedTime?: Moment;
  indicatorId?: number;
  indicatorType?: IndicatorType;
  indicatorName?: string;
  attributedEntityType?: EntityType;
  attributedEntityName?: string;
  indicatorSourceType?: SourceType;
  indicatorSourceName?: string;
  indicatorSourceLink?: string;
}

export const defaultValue: Readonly<IHealthIndicatorObservation> = {};
