import { TransactionActionTypes } from './transaction.types';
import axios from 'axios';

const filterTransactionsByAccountId = (request) => async (dispatch) => {
  try{
    const response = await axios.post(`http://localhost:8080/api/bo/transaction/filter`,request).catch(err => {
      
        throw new Error(err.response);
    });
    dispatch({
      type: TransactionActionTypes.GET_TRANSACTIONS_BY_SEARCH,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: TransactionActionTypes.TRANSACTION_ERROR,
      payload: error.message
    });
}};


const getTransactions = (id) => async (dispatch) => {
  try{
    const response = await axios.get(`http://localhost:8080/api/bo/transaction/account/${id}`).catch(err => {
      
        throw new Error(err.response);
    });
    dispatch({
      type: TransactionActionTypes.GET_TRANSACTIONS,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: TransactionActionTypes.TRANSACTION_ERROR,
      payload: error.message
    });
}};

const addTransaction = (request) => async (dispatch) => {
  try{
    const response = await axios.post(`http://localhost:8080/api/bo/transaction`,request).catch(err => {
      
        throw new Error(err.response);
    });
    dispatch({
      type: TransactionActionTypes.SET_TRANSACTION,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: TransactionActionTypes.TRANSACTION_ERROR,
      payload: error.message
    });
}};

const deleteTransaction = (id) => async (dispatch) => {
  try{
    const response = await axios.delete(`http://localhost:8080/api/bo/transaction/${id}`).catch(err => {
      
        throw new Error(err.response);
    });
    dispatch({
      type: TransactionActionTypes.DELETE_TRANSACTION,
      payload: id,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: TransactionActionTypes.TRANSACTION_ERROR,
      payload: error.message
    });
}};

export default{getTransactions,filterTransactionsByAccountId,addTransaction,deleteTransaction};