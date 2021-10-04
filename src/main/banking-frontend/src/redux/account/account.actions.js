import { AccountActionTypes } from './account.types';
import axios from 'axios';

const getAccountById = (id) => async (dispatch) => {
  try{
    const response = await axios.get(`http://localhost:8080/api/bo/account/${id}`).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: AccountActionTypes.GET_ACCOUNT,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: AccountActionTypes.ACCOUNT_ERROR,
      payload: error.message
    });
}};

const getAccounts = (id) => async (dispatch) => {
  try{
    const response = await axios.get(`http://localhost:8080/api/bo/account/customer/${id}`).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: AccountActionTypes.GET_ACCOUNTS,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: AccountActionTypes.ACCOUNT_ERROR,
      payload: error.message
    });
}};

const addAccount = (request) => async (dispatch) => {
  try{
    const response = await axios.post(`http://localhost:8080/api/bo/account`,request).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: AccountActionTypes.SET_ACCOUNT,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: AccountActionTypes.ACCOUNT_ERROR,
      payload: error.message
    });
}};

const deleteAccount = (id) => async (dispatch) => {
  try{
    const response = await axios.delete(`http://localhost:8080/api/bo/account/${id}`).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: AccountActionTypes.DELETE_ACCOUNT,
      payload: id,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: AccountActionTypes.ACCOUNT_ERROR,
      payload: error.message
    });
}};


export default {getAccountById,getAccounts,addAccount,deleteAccount};