import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-open-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './open-account.component.html',
  styleUrls: ['./open-account.component.css']
})
export class OpenAccountComponent {
  onSubmit(form: any) {
    const clientId = form.value.clientId;
    const accountType = form.value.accountType;
    const accountId = this.generateAccountId();
    const initialBalance = 0;

    console.log('Conta criada com sucesso!');
    console.log(`ID do Cliente: ${clientId}`);
    console.log(`Tipo de Conta: ${accountType}`);
    console.log(`ID da Conta: ${accountId}`);
    console.log(`Saldo Inicial: ${initialBalance}`);
    alert(`Conta criada com sucesso! ID da Conta: ${accountId}`);
  }

  private generateAccountId(): string {
    return Math.floor(100000 + Math.random() * 900000).toString();
  }
}