import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { Account } from '../../services/models/account.model';

@Component({
  selector: 'app-account-registration',
  imports: [FormsModule, ReactiveFormsModule],
  standalone: true, 
  templateUrl: './account-registration.component.html',
  styleUrls: ['./account-registration.component.css']
})
export class AccountRegistrationComponent {
  accountForm: FormGroup;
  account!: Account;

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.accountForm = this.fb.group({
      accountNumber: ['', Validators.required],
      customerId: ['', Validators.required],
      accountType: ['', Validators.required],
      initialBalance: ['', [Validators.required, Validators.min(0)]]
    });
  }

  onSubmit() {
    if (this.accountForm.valid) {
      this.apiService.createAccount(this.accountForm.value).subscribe(response => {
        // Handle successful account registration
        console.log('Account registered successfully', response);
        this.accountForm.reset();
      }, error => {
        // Handle error during account registration
        console.error('Error registering account', error);
      });
    }
  }
}