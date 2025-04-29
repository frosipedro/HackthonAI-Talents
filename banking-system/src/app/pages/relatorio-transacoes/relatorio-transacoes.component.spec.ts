import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioTransacoesComponent } from './relatorio-transacoes.component';

describe('RelatorioTransacoesComponent', () => {
  let component: RelatorioTransacoesComponent;
  let fixture: ComponentFixture<RelatorioTransacoesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RelatorioTransacoesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioTransacoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
