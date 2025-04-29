import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class MensagemSweetService {

  constructor() { }
  sucesso(mensagem: string) {
    Swal.fire(
        {
        title: 'Sucesso',
          timer: 5000,
          text: mensagem,
          icon: 'success',
          confirmButtonText: 'Ok'
    }
    )
  }

  erro(mensagem: string) {
    Swal.fire(
        {
          title: 'Erro',
          timer: 5000,
          text: mensagem,
          icon: 'error',
          confirmButtonText: 'Ok'
        }
    )
  }
}
