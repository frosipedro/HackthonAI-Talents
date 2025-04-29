import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

interface Transaction {
  type: string;
  amount: number;
  date: Date;
}

@Component({
  selector: 'app-relatorio-transacoes',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    ReactiveFormsModule
  ],
  templateUrl: './relatorio-transacoes.component.html',
  styleUrls: ['./relatorio-transacoes.component.css']
})
export class RelatorioTransacoesComponent implements OnInit {
  form: FormGroup;
  displayedColumns: string[] = ['type', 'amount', 'date'];
  dataSource = new MatTableDataSource<Transaction>();
  totalTransactions = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.form = this.fb.group({
      accountNumber: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    });
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
  }

  searchTransactions() {
    if (this.form.valid) {
      const accountNumber = this.form.get('accountNumber')?.value;
      
      // COLOCAR A CHAMADA DO BACKEND AQUI
      this.fetchTransactions(accountNumber);
    } else {
      this.snackBar.open('Por favor, insira um número de conta válido', 'Fechar', {
        duration: 3000
      });
    }
  }

  private fetchTransactions(accountNumber: string) {
    // SUBSTITUTO DA CHAMADA DA API
    const mockTransactions: Transaction[] = [
      { type: 'Depósito', amount: 1000, date: new Date('2024-04-28') },
      { type: 'Saque', amount: 500, date: new Date('2024-04-27') },
      { type: 'Depósito', amount: 2000, date: new Date('2024-04-26') }
    ];

    this.dataSource.data = mockTransactions;
    this.totalTransactions = mockTransactions.length;
  }

}