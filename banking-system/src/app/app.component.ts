import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

import { SidenavComponent } from './layout/sidenav/sidenav.component';


@Component({
  standalone: true,
  selector: 'app-root',
  imports: [RouterOutlet,MatSlideToggleModule,SidenavComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})

export class AppComponent {
  title = 'banking-system';
}
