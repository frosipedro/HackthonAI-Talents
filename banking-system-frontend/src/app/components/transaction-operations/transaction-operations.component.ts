import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-transaction-operations',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './transaction-operations.component.html',
  styleUrls: ['./transaction-operations.component.css']
})
export class TransactionOperationsComponent {
  transaction = {
    accountId: '',
    type: 'Deposit',
    amount: 0
  };
  
  message: string = '';
  isSuccess: boolean = false;
  isSubmitted: boolean = false;

  constructor(private apiService: ApiService) {}

  onSubmit() {
    this.isSubmitted = true;
    
    // Basic validation
    if (!this.transaction.accountId || this.transaction.amount <= 0) {
      this.message = 'Please fill all fields correctly';
      this.isSuccess = false;
      return;
    }

    // Call the appropriate API method based on transaction type
    if (this.transaction.type === 'Deposit') {
      this.apiService.deposit(this.transaction.accountId, this.transaction.amount).subscribe(
        (response) => {
          this.handleSuccess(`Deposit of $${this.transaction.amount} completed successfully`);
        },
        (error) => {
          this.handleError(error);
        }
      );
    } else {
      this.apiService.withdraw(this.transaction.accountId, this.transaction.amount).subscribe(
        (response) => {
          this.handleSuccess(`Withdrawal of $${this.transaction.amount} completed successfully`);
        },
        (error) => {
          this.handleError(error);
        }
      );
    }
  }

  handleSuccess(message: string) {
    this.message = message;
    this.isSuccess = true;
    this.resetForm();
  }

  handleError(error: any) {
    this.message = `Error: ${error.error?.message || error.message || 'Transaction failed'}`;
    this.isSuccess = false;
  }

  resetForm() {
    this.transaction = {
      accountId: '',
      type: 'Deposit',
      amount: 0
    };
  }
}