import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from './models/customer.model';
import { Account } from './models/account.model';
import { Transaction } from './models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl: string = 'http://localhost:3000'; 

  constructor(private http: HttpClient) { }

  registerCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}/customers`, customer);
  }

  createAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${this.baseUrl}/accounts`, account);
  }

  getCustomerById(customerId: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/customers/${customerId}`);
  }

  getTransactions(accountNumber: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.baseUrl}/accounts/${accountNumber}/transactions`);
  }
  
  deposit(accountId: string, amount: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/accounts/deposit`, { accountId, amount });
  }

  withdraw(accountId: string, amount: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/accounts/withdraw`, { accountId, amount });
  }
}