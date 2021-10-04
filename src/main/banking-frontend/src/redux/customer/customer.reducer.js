import { CustomerActionTypes } from './customer.types';

const INITIAL_STATE = {
    customers: [],
    customer: {accounts:[]},
    error: null
}

const customerReducer = (state = INITIAL_STATE, action) =>  {
    switch(action.type){
        case CustomerActionTypes.GET_CUSTOMER:
            return {
              ...state,
              customer: action.payload,
              error: null
            };
        case CustomerActionTypes.SET_CUSTOMER:
        return {
            ...state,
            customers: [...state.customers, action.payload],
            error: null
        };
        case CustomerActionTypes.UPDATE_CUSTOMER:
            return {
              ...state,
              customers: [...state.customers.filter(customer => customer.id !== action.payload.id), action.payload],
              error: null
            };
        case CustomerActionTypes.DELETE_CUSTOMER:
        return {
            ...state,
            customers: state.customers.filter(customer => customer.id !== action.payload),
            error: null
        };
        case CustomerActionTypes.GET_CUSTOMERS_BY_SEARCH:
            return {
                ...state,
                customers: action.payload,
                error: null
            };
        case CustomerActionTypes.CUSTOMERS_ERROR:
            return {
                ...state,
                error: action.payload
            };    
        
        default:
            return state;
    }
}

export default customerReducer;