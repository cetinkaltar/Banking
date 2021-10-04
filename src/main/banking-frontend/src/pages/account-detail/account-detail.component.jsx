import React,{useEffect,useState} from "react";
import { connect } from 'react-redux';
import transactionActions from '../../redux/transaction/transaction.actions'; 
import { useParams } from "react-router-dom";
import moment from 'moment';


import CRUDTable,
{
  Fields,
  Field,
  CreateForm,
  DeleteForm,
} from 'react-crud-table';

const {getTransactions,filterTransactionsByAccountId,addTransaction,deleteTransaction} = transactionActions;


const AccountDetailPage = ({getTransactions,filterTransactionsByAccountId,addTransaction,deleteTransaction,transactions, error}) =>{
    const { id } = useParams();
   const [dayFrom,setDayFrom] = useState(moment().format("YYYY-MM-DD"));
   const [dayTo,setDayTo] = useState(moment().format("YYYY-MM-DD"));

    useEffect(()=>{
      getTransactions(id);
  },[]);

  const handleCreate= (data) => 
   addTransaction({
    fromAccountId: data.fromAccountId,
    toAccountId: data.toAccountId,
    amount: data.amount,
    description: data.description
    });
  const handleDelete=(data) => 
    deleteTransaction(data.id);
  const handleFilter= () => 
    filterTransactionsByAccountId({
      accountId: parseInt(id,10),
      dayFrom: dayFrom,
      dayTo: dayTo}
    );
  

const styles = {
  container: { margin: 'auto', width: 'fit-content' },
};

   
    return (
    <div>
     {error ? (<div>{error}</div>):(
         <div>

         <div className="row example-wrapper">
         <div className="col-xs-12 col-sm-6 offset-sm-3 example-col">
           <div className="card">
             <div className="card-block">
                 <fieldset>
                   <legend>Please select period for filtering transactions:</legend>
                   <label className="k-form-field">
                     <span>From Date</span>
                     <input type="date" onChange= {(e)=>setDayFrom(moment(e.target.value).format("YYYY-MM-DD"))}
                     />
                   </label>
                   <label className="k-form-field">
                     <span>To Date</span>
                     <input type="date" onChange= {(e)=>setDayTo(moment(e.target.value).format("YYYY-MM-DD"))}
                     />
                   </label>
                 </fieldset>
                 <button
                   onClick= {handleFilter}
                   > Search</button>
             </div>
           </div>
         </div>
       </div>

         <div style={styles.container}>
         <CRUDTable
           caption="Account Transactions"
           items={transactions}
         >
           <Fields>
             <Field
               name="id"
               label="Id"
               hideInCreateForm
               readOnly
             />
             <Field
               name="fromAccountId"
               label="From Account Id"
               placeholder="From Account Id"
             />
             <Field
               name="toAccountId"
               label="To Account Id"
               placeholder="To Account Id"
             />
             <Field
               name="amount"
               label="Amount"
               placeholder="Amount"
             />
             <Field
               name="description"
               label="Description"
               placeholder="Description"
             />
             <Field
               name="date"
               label="Transaction Date"
               placeholder="Date"
               hideInCreateForm
             />
             <Field
               name="type"
               label="Payment Way"
               placeholder="Payment Way"
               hideInCreateForm
             />
           
           </Fields>
           <CreateForm
             title="Transaction Creation"
             message="Create a new transaction!"
             trigger="Create Transaction"
             onSubmit={task => handleCreate(task)}
             submitText="Create"
             validate={(values) => {
               const errors = {};
               if (!values.fromAccountId) {
                 errors.fromAccountId = 'Please, provide transaction\'s fromAccountId';
               }
     
               if (!values.toAccountId) {
                 errors.toAccountId = 'Please, provide transaction\'s toAccountId';
               }

               if (!values.amount) {
                errors.amount = 'Please, provide transaction\'s amount';
              }

              if (!values.description) {
                errors.description = 'Please, provide transaction\'s description';
              }
     
               return errors;
             }}
           />
     
           <DeleteForm
             title="Transaction Delete Process"
             message="Are you sure you want to delete the transaction?"
             trigger="Delete"
             onSubmit={task => handleDelete(task)}
             submitText="Delete"
             validate={(values) => {
               const errors = {};
               if (!values.id) {
                 errors.id = 'Please, provide id';
               }
               return errors;
             }}
           />
         </CRUDTable>
       </div>
         </div>
     )
    }
    </div>
)};
    
export default connect((state) => ({
    transactions: state?.transaction.transactions,
    error: state?.transaction?.error
  }),{filterTransactionsByAccountId,getTransactions,addTransaction,deleteTransaction})(AccountDetailPage);