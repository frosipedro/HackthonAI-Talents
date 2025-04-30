import { Routes } from '@angular/router';
import { MainMenuComponent } from './components/main-menu/main-menu.component';
import { CustomerRegistrationComponent } from './components/customer-registration/customer-registration.component';
import { AccountRegistrationComponent } from './components/account-registration/account-registration.component';
import { CustomerInfoComponent } from './components/customer-info/customer-info.component';
import { TransactionReportsComponent } from './components/transaction-reports/transaction-reports.component';
import { TransactionOperationsComponent } from './components/transaction-operations/transaction-operations.component';

export const routes: Routes = [
    { path: '', redirectTo: '/menu', pathMatch: 'full' },
    { path: 'menu', component: MainMenuComponent },
    { path: 'register-customer', component: CustomerRegistrationComponent },
    { path: 'register-account', component: AccountRegistrationComponent },
    { path: 'customer-info', component: CustomerInfoComponent },
    { path: 'transaction-reports', component: TransactionReportsComponent },
    { path: 'transactions', component: TransactionOperationsComponent },
    { path: '**', redirectTo: '/menu' } // Rota de fallback
];

