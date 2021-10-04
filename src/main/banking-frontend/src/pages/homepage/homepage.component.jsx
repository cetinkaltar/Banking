import React,{useEffect} from "react";
import { connect } from 'react-redux';
import customerActions from '../../redux/customer/customer.actions'; 
import CRUDTable,
{
  Fields,
  Field,
  CreateForm,
  UpdateForm,
  DeleteForm,
} from 'react-crud-table';

const {searchCustomers,addCustomer,deleteCustomer,updateCustomer} = customerActions;


const HomePage = ({searchCustomers,addCustomer,deleteCustomer,updateCustomer, customers, error,history}) =>{
    useEffect(()=>{
        searchCustomers("");
        console.log("useeffect");
    },[]);

    useEffect(()=>{
      console.log("useeffect state changed");
  },[customers]);


  const handleCreate = (data) => 
   addCustomer({
      firstName: data.firstName,
      lastName: data.lastName
    });

  const handleUpdate = (data) => 
    updateCustomer({
      id: data.id,
      payload:{
        firstName: data.firstName,
        lastName: data.lastName
      }
    });
  
  const handleDelete = (data) => 
    deleteCustomer(data.id);
 

const styles = {
  container: { margin: 'auto', width: 'fit-content' },
};

    const onChangeSearchText = (e) => {
        const searchTitle = e.target.value;
        searchCustomers(searchTitle);
      };

    return (
    <div>
     {error ? (<div>{error}</div>):(
         <div>
         
         <div>
         <input
            type="text"
            className="form-control"
            placeholder="Search by first name or last name"
            onChange={onChangeSearchText}
          />
         </div>
         <div style={styles.container}>
         <CRUDTable
           caption="Customers"
          items={customers}
         >
           <Fields>
             <Field
               name="id"
               label="Id"
               hideInCreateForm
               readOnly
             />
             <Field
               name="firstName"
               label="First Name"
               placeholder="Title"
             />
             <Field
             name="lastName"
             label="Last Name"
             placeholder="Last Name"
           />
           <Field
           label=""
           tableValueResolver= {({id})=><button onClick={()=>history.push(`/customer/${id}`)}>details...</button>}
           hideInCreateForm
           hideInUpdateForm
         />
           </Fields>
           <CreateForm
             title="Customer Creation"
             message="Create a new customer!"
             trigger="Create Customer"
             onSubmit={task => handleCreate(task)}
             submitText="Create"
             validate={(values) => {
               const errors = {};
               if (!values.firstName) {
                 errors.firstName = 'Please, provide customer\'s first name';
               }
     
               if (!values.lastName) {
                 errors.lastName = 'Please, provide customer\'s last name';
               }
     
               return errors;
             }}
           />
     
           <UpdateForm
             title="Customer Update Process"
             message="Customer task"
             trigger="Update"
             onSubmit={task => handleUpdate(task)}
             submitText="Update"
             validate={(values) => {
               const errors = {};
     
               if (!values.id) {
                 errors.id = 'Please, provide id';
               }
     
               if (!values.firstName) {
                 errors.firstName = 'Please, provide customer\'s first name';
               }
     
               if (!values.lastName) {
                 errors.lastName = 'Please, provide customer\'s last name';
               }
     
               return errors;
             }}
           />
     
           <DeleteForm
             title="Customer Delete Process"
             message="Are you sure you want to delete the customer?"
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
    customers: state?.customer.customers,
    error: state?.customer?.error
}),{searchCustomers,addCustomer,deleteCustomer,updateCustomer})(HomePage);