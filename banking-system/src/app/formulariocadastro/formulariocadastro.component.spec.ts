import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariocadastroComponent } from './formulariocadastro.component';

describe('FormulariocadastroComponent', () => {
  let component: FormulariocadastroComponent;
  let fixture: ComponentFixture<FormulariocadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormulariocadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormulariocadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
