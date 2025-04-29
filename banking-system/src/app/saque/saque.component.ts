import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {ChangeDetectionStrategy ,Component } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'app-saque',
  imports: [MatSlideToggleModule,MatSelectModule,MatInputModule,MatFormFieldModule,MatIconModule,MatDividerModule,MatButtonModule],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SaqueComponent {

}
