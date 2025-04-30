import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MensagemSweetService } from '../shared/servicos/mensagem-sweet.service';


@Component({
  standalone: true,
  selector: 'app-formulariocadastro',
  imports: [ReactiveFormsModule],
  templateUrl: './formulariocadastro.component.html',
  styleUrl: './formulariocadastro.component.css'
})
export class FormulariocadastroComponent {
  clienteForm: FormGroup;

  constructor(
    private roteador: Router,
    private mensagemService: MensagemSweetService,
    private fb: FormBuilder
  ) {
    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', Validators.required, Validators.email],
      dataNascimento: ['', Validators.required]
    });
  }

  // carregarUsuario() {
    // const usuarioSalvo = localStorage.getItem('usuario');
    // if (usuarioSalvo) {
    //   const usuario = JSON.parse(usuarioSalvo);
    //   this.nomeUsuario = usuario.nome;
    //   this.emailUsuario = usuario.email;
    //   this.dataNascimento = usuario.dataNascimento;
    // }
  // }

  onSubmit() {
    console.warn(this.clienteForm.value);
    console.log('aiaiaiia');

    if (this.clienteForm.invalid) {
      this.mensagemService.erro('Preencha todos os campos obrigatórios!');
      return;
    }

    const cliente = this.clienteForm.value;

    console.log(cliente);

    console.log('Cliente cadastrado localmente:', cliente);
    this.mensagemService.sucesso('Cliente cadastrado com sucesso!');
    this.roteador.navigate(['/clientes']);
  }
}
