import React,{useEffect} from "react";
import { connect } from 'react-redux';
import { useParams } from "react-router-dom";
import accountActions from '../../redux/account/account.actions';
import CRUDTable,
{
  Fields,
  Field,
  CreateForm,
  DeleteForm,
} from 'react-crud-table';

const {getAccounts,addAccount,deleteAccount} = accountActions;


const CustomerDetailPage = ({getAccounts,addAccount,deleteAccount,accounts, error,history}) =>{
    const { id } = useParams();
  useEffect(()=>{
    getAccounts(id);
},[]);


  const handleCreate =  (data) => 
   addAccount({
      iban: data.iban,
      balance: data.balance,
      customerId: id
    });
   
  const handleDelete  =(data) => 
    deleteAccount(data.id);
    
 

const styles = {
  container: { margin: 'auto', width: 'fit-content' },
};

    return (
    <div>
     {error ? (<div>{error}</div>):(
         <div>
         <div style={styles.container}>
         <CRUDTable
           caption="Customer Accounts"
           items={accounts}
         >
           <Fields>
             <Field
               name="id"
               label="Account Id"
               hideInCreateForm
               readOnly
             />
             <Field
               name="iban"
               label="Iban"
               placeholder="Iban"
             />
             <Field
             name="balance"
             label="Balance"
             placeholder="Balance"
           />
           <Field
           label=""
           tableValueResolver= {({id})=><button onClick={()=>history.push(`/account/${id}`)}>details...</button>}
           hideInCreateForm
         />
           </Fields>
           <CreateForm
             title="Account Creation"
             message="Create a new account!"
             trigger="Create Account"
             onSubmit={task => handleCreate(task)}
             submitText="Create"
             validate={(values) => {
               const errors = {};
               if (!values.iban) {
                 errors.firstName = 'Please, provide account\'s first name';
               }
     
               if (!values.balance) {
                 errors.lastName = 'Please, provide balance\'s last name';
               }
     
               return errors;
             }}
           />
     
           <DeleteForm
             title="Account Delete Process"
             message="Are you sure you want to delete the account?"
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
    accounts: state?.account?.accounts,
    error: state?.account?.error
  }),{getAccounts,addAccount,deleteAccount})(CustomerDetailPage);