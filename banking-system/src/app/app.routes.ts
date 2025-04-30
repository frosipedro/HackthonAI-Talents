import { Routes } from '@angular/router';
import { SaqueComponent } from './saque/saque.component';

export const routes: Routes = [{path: "saque",component: SaqueComponent}];
import { FormulariocadastroComponent } from './formulariocadastro/formulariocadastro.component'; 

export const routes: Routes = [
    {
        path: 'formulario-cliente',
        component: FormulariocadastroComponent
    },
];
