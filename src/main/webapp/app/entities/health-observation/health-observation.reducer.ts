import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHealthObservation, defaultValue } from 'app/shared/model/health-observation.model';

export const ACTION_TYPES = {
  FETCH_HEALTHOBSERVATION_LIST: 'healthObservation/FETCH_HEALTHOBSERVATION_LIST',
  FETCH_HEALTHOBSERVATION: 'healthObservation/FETCH_HEALTHOBSERVATION',
  CREATE_HEALTHOBSERVATION: 'healthObservation/CREATE_HEALTHOBSERVATION',
  UPDATE_HEALTHOBSERVATION: 'healthObservation/UPDATE_HEALTHOBSERVATION',
  DELETE_HEALTHOBSERVATION: 'healthObservation/DELETE_HEALTHOBSERVATION',
  RESET: 'healthObservation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHealthObservation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type HealthObservationState = Readonly<typeof initialState>;

// Reducer

export default (state: HealthObservationState = initialState, action): HealthObservationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HEALTHOBSERVATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HEALTHOBSERVATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HEALTHOBSERVATION):
    case REQUEST(ACTION_TYPES.UPDATE_HEALTHOBSERVATION):
    case REQUEST(ACTION_TYPES.DELETE_HEALTHOBSERVATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HEALTHOBSERVATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HEALTHOBSERVATION):
    case FAILURE(ACTION_TYPES.CREATE_HEALTHOBSERVATION):
    case FAILURE(ACTION_TYPES.UPDATE_HEALTHOBSERVATION):
    case FAILURE(ACTION_TYPES.DELETE_HEALTHOBSERVATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEALTHOBSERVATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_HEALTHOBSERVATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HEALTHOBSERVATION):
    case SUCCESS(ACTION_TYPES.UPDATE_HEALTHOBSERVATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HEALTHOBSERVATION):
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

const apiUrl = 'api/health-observations';

// Actions

export const getEntities: ICrudGetAllAction<IHealthObservation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HEALTHOBSERVATION_LIST,
    payload: axios.get<IHealthObservation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IHealthObservation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HEALTHOBSERVATION,
    payload: axios.get<IHealthObservation>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHealthObservation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HEALTHOBSERVATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHealthObservation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HEALTHOBSERVATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHealthObservation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HEALTHOBSERVATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
