import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import customerReducer from './customer/customer.reducer';
import accountReducer from './account/account.reducer';
import transactionReducer from './transaction/transaction.reducer';

const persistConfig ={
  key:'root',
  storage,
  whitelist: ['cart']
}

const rootReducer = combineReducers({
  customer: customerReducer,
  account: accountReducer,
  transaction: transactionReducer

});

export default persistReducer(persistConfig, rootReducer);