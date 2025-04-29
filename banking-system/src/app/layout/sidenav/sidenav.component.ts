import { Component } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; 


@Component({
  selector: 'app-sidenav',
  standalone: true,
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.css',
  imports: [RouterModule, MatMenuModule, MatIconModule, CommonModule]
})
export class SidenavComponent {
   isCollapsed = false;
   itemSelecionado = -1;
 
   menuItems = [
     { label: 'Home', icon: 'home', link: '/tela-principal' },
     { label: 'Registrar', icon: 'person_add', link: '/formulario-cliente' },
     { label: 'Abrir conta', icon: 'savings', link: '/abrirconta' },
     { label: 'Depositar', icon: 'currency_exchange', link: '/minhascaronas' },
     { label: 'Sacar', icon: 'payments', link: '/help' },
     { label: 'Relatóios', icon: 'analytics', link: '/minhascaronas' },
   ];
 
   // Método para alternar o estado do menu (colapsado/expandido)
   toggleSidebar() {
     this.isCollapsed = !this.isCollapsed;
   }
 
   // Método para selecionar um item do menu
   selecionarItem(index: number, event: Event) {
     event.stopPropagation();
     this.itemSelecionado = index;
   }
}