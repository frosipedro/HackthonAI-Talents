import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-client-info',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './client-info.component.html',
  styleUrls: ['./client-info.component.css']
})
export class ClientInfoComponent {
  clientId: string = '';
  showClientInfo: boolean = false;
  client: any = null;

  constructor() {
    console.log('Componente iniciado');
  }

  searchClient(): void {
    console.log('Método searchClient chamado');
    alert('Botão clicado!');
    
    if (!this.clientId) {
      alert('Por favor, insira um ID');
      return;
    }

    this.client = {
      id: this.clientId,
      name: 'Cliente Teste',
      email: 'teste@email.com',
      birthDate: new Date(),
      accounts: []
    };

    this.showClientInfo = true;
    console.log('Cliente:', this.client);
  }

  getAccountsCount(): number {
    return this.client?.accounts?.length || 0;
  }
}
