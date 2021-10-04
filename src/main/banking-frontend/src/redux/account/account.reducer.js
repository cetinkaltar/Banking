import { AccountActionTypes } from './account.types';

const INITIAL_STATE = {
    account: {transactions: []},
    accounts: [],
    error: null
}

const accountReducer = (state = INITIAL_STATE, action) =>  {
    switch(action.type){
        case AccountActionTypes.GET_ACCOUNT:
            return {
              ...state,
              account: action.payload,
              error: null
            };
        case AccountActionTypes.GET_ACCOUNTS:
        return {
            ...state,
            accounts: action.payload,
            error: null
        };
        case AccountActionTypes.SET_ACCOUNT:
            return {
                ...state,
                accounts: [...state.accounts, action.payload],
                error: null
            };
        case AccountActionTypes.DELETE_ACCOUNT:
            return {
            ...state,
            accounts:  state.accounts.filter(acc => acc.id !== action.payload),
            error: null
            };
        case AccountActionTypes.ACCOUNT_ERROR:
            return {
            ...state,
            error: action.payload
            };
        
        default:
            return state;
    }
}

export default accountReducer;