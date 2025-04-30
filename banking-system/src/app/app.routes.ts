import { Routes } from '@angular/router';
import { SaqueComponent } from './saque/saque.component';

import { FormulariocadastroComponent } from './formulariocadastro/formulariocadastro.component'; 

export const routes: Routes = [
    {
        path: 'formulario-cliente',
        component: FormulariocadastroComponent
    },
    {
        path: "saque",
        component: SaqueComponent
    }
];
