import { TransactionActionTypes } from './transaction.types';

const INITIAL_STATE = {
    transactions: [],
    error: null
}

const transactionReducer = (state = INITIAL_STATE, action) =>  {
    switch(action.type){
        case TransactionActionTypes.GET_TRANSACTIONS:
            return {
              ...state,
              transactions: action.payload,
              error: null
            };
        case TransactionActionTypes.GET_TRANSACTIONS_BY_SEARCH:
            return {
              ...state,
              transactions: action.payload,
              error: null
            };
        case TransactionActionTypes.SET_TRANSACTION:
            return {
                ...state,
                transactions: [...state.transactions, action.payload],
                error: null
            };
        case TransactionActionTypes.DELETE_TRANSACTION:
            return {
                ...state,
                transactions: state.transactions.filter(transaction => transaction.id !== action.payload),
                error: null
            };
        case TransactionActionTypes.TRANSACTION_ERROR:
            return {
                ...state,
                error: action.payload
            };
        
        default:
            return state;
    }
}

export default transactionReducer;