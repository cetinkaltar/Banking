import { CustomerActionTypes } from './customer.types';
import axios from 'axios';

const getCustomerById = (id) => async (dispatch) => {
  try{
    const response = await axios.get(`http://localhost:8080/api/bo/customer/${id}`).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: CustomerActionTypes.GET_CUSTOMER,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: CustomerActionTypes.CUSTOMERS_ERROR,
      payload: error.message
    });
}};

const addCustomer = (request) => async (dispatch) => {
  try{
    const response = await axios.post(`http://localhost:8080/api/bo/customer`,request).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: CustomerActionTypes.SET_CUSTOMER,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: CustomerActionTypes.CUSTOMERS_ERROR,
      payload: error.message
    });
}};


const updateCustomer = (request) => async (dispatch) => {
  try{
    const response = await axios.put(`http://localhost:8080/api/bo/customer/${request.id}`,request.payload).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: CustomerActionTypes.UPDATE_CUSTOMER,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: CustomerActionTypes.CUSTOMERS_ERROR,
      payload: error.message
    });
}};

const deleteCustomer = (id) => async (dispatch) => {
  try{
    const response = await axios.delete(`http://localhost:8080/api/bo/customer/${id}`).catch(err => {
        throw new Error(err.response);
    });
    dispatch({
      type: CustomerActionTypes.DELETE_CUSTOMER,
      payload: id,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: CustomerActionTypes.CUSTOMERS_ERROR,
      payload: error.message
    });
}};

 const searchCustomers = (query) => async (dispatch) => {
  try{
    const response = await axios.get(`http://localhost:8080/api/bo/customer/filter`,{params: {searchQuery: `${query}`}}).catch(err => {
      
        throw new Error(err.response);
    });
    dispatch({
      type: CustomerActionTypes.GET_CUSTOMERS_BY_SEARCH,
      payload: response.data,
    });
  }catch(error){
    console.log("test ", error.message);
    dispatch({
      type: CustomerActionTypes.CUSTOMERS_ERROR,
      payload: error.message
    });
}};

export default {searchCustomers,deleteCustomer,updateCustomer,addCustomer,getCustomerById};

