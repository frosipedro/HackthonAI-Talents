import { Routes } from '@angular/router';
import { SaqueComponent } from './saque/saque.component';
import { RelatorioTransacoesComponent } from './pages/relatorio-transacoes/relatorio-transacoes.component';
import { FormulariocadastroComponent } from './formulariocadastro/formulariocadastro.component'; 
import { ClientInfoComponent } from './client-info/client-info.component';

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
    {
        path: 'client-info',
        component: ClientInfoComponent
    }
      
];
