import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { SidenavComponent } from './layout/sidenav/sidenav.component';


@Component({
  standalone: true,
  selector: 'app-root',
  imports: [RouterOutlet,MatSlideToggleModule,SidenavComponent,FormsModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})

export class AppComponent {
  title = 'banking-system';
}
