import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Transaction } from '../../services/models/transaction.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transaction-reports',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './transaction-reports.component.html',
  styleUrls: ['./transaction-reports.component.css']
})
export class TransactionReportsComponent implements OnInit {
  transactions: Transaction[] = [];
  accountNumber: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {}

  fetchTransactions(): void {
    if (this.accountNumber) {
      this.apiService.getTransactions(this.accountNumber).subscribe(
        (data: Transaction[]) => {
          this.transactions = data;
        },
        (error) => {
          console.error('Error fetching transactions', error);
        }
      );
    }
  }
}