import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHealthIndicatorObservation, defaultValue } from 'app/shared/model/health-indicator-observation.model';

export const ACTION_TYPES = {
  FETCH_HEALTHINDICATOROBSERVATION_LIST: 'healthIndicatorObservation/FETCH_HEALTHINDICATOROBSERVATION_LIST',
  FETCH_HEALTHINDICATOROBSERVATION: 'healthIndicatorObservation/FETCH_HEALTHINDICATOROBSERVATION',
  CREATE_HEALTHINDICATOROBSERVATION: 'healthIndicatorObservation/CREATE_HEALTHINDICATOROBSERVATION',
  UPDATE_HEALTHINDICATOROBSERVATION: 'healthIndicatorObservation/UPDATE_HEALTHINDICATOROBSERVATION',
  DELETE_HEALTHINDICATOROBSERVATION: 'healthIndicatorObservation/DELETE_HEALTHINDICATOROBSERVATION',
  RESET: 'healthIndicatorObservation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHealthIndicatorObservation>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type HealthIndicatorObservationState = Readonly<typeof initialState>;

// Reducer

export default (state: HealthIndicatorObservationState = initialState, action): HealthIndicatorObservationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HEALTHINDICATOROBSERVATION):
    case REQUEST(ACTION_TYPES.UPDATE_HEALTHINDICATOROBSERVATION):
    case REQUEST(ACTION_TYPES.DELETE_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION):
    case FAILURE(ACTION_TYPES.CREATE_HEALTHINDICATOROBSERVATION):
    case FAILURE(ACTION_TYPES.UPDATE_HEALTHINDICATOROBSERVATION):
    case FAILURE(ACTION_TYPES.DELETE_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HEALTHINDICATOROBSERVATION):
    case SUCCESS(ACTION_TYPES.UPDATE_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HEALTHINDICATOROBSERVATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/health-indicator-observations';

// Actions

export const getEntities: ICrudGetAllAction<IHealthIndicatorObservation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION_LIST,
    payload: axios.get<IHealthIndicatorObservation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IHealthIndicatorObservation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HEALTHINDICATOROBSERVATION,
    payload: axios.get<IHealthIndicatorObservation>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHealthIndicatorObservation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HEALTHINDICATOROBSERVATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IHealthIndicatorObservation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HEALTHINDICATOROBSERVATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHealthIndicatorObservation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HEALTHINDICATOROBSERVATION,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
