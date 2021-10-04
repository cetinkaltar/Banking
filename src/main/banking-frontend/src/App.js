import './App.css';
import Header from './components/header/header.component';
import { Route, Switch} from'react-router-dom';
import HomePage from './pages/homepage/homepage.component';
import CustomerDetailPage from './pages/customer-detail/customer-detail.component';
import AccountDetailPage from './pages/account-detail/account-detail.component';

function App() {
  return (
    <div className="App">
     <Header />
     <Switch>
     <Route exact path="/" component={HomePage} />
     <Route exact path="/customer/:id" component={CustomerDetailPage} />
     <Route exact path="/account/:id" component={AccountDetailPage} />
     </Switch>
    </div>
  );
}

export default App;
