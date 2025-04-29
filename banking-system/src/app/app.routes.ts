import { Routes } from '@angular/router';
import { RelatorioTransacoesComponent } from './pages/relatorio-transacoes/relatorio-transacoes.component';

export const routes: Routes = [
  { path: '', redirectTo: 'relatorio-transacoes', pathMatch: 'full' },
  { path: 'relatorio-transacoes', component: RelatorioTransacoesComponent },
  // ... other routes
];