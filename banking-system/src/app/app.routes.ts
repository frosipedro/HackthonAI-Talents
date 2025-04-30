import { Routes } from '@angular/router';
import { SaqueComponent } from './saque/saque.component';
import { RelatorioTransacoesComponent } from './pages/relatorio-transacoes/relatorio-transacoes.component';
import { FormulariocadastroComponent } from './formulariocadastro/formulariocadastro.component'; 

export const routes: Routes = [
    {
        path: 'formulario-cliente',
        component: FormulariocadastroComponent
    },
    {
        path: "saque",
        component: SaqueComponent
    },
    { 
        path: 'relatorio-transacoes',
        component: RelatorioTransacoesComponent 
    },
];
