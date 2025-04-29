export class Cliente {
    constructor(public id?: string,
                public nome?: string,
                public email?:string,
                public dataNascimento?: number,
    ){}
}
export const CLIENTES: Cliente[] = [
]