<template>
    <main>
        <h3>Cadastro Empresa</h3>
        
        <div v-if="mostrarAlertaCNPJInvalido" class="filter-download">
            <p class="title-popup">CNPJ Inválido! Revise e tente novamente</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaCNPJInvalido = false">OK</button>
        </div>

        <div v-if="mostrarAlertaConstraint" class="filter-download">
            <p class="title-popup">Já existe uma empresa com esse CNPJ. Revise e tente novamente</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaConstraint = false">OK</button>
        </div>

        <div v-if="mostrarAlertaOutrosErros" class="filter-download">
            <p class="title-popup">{{ outrosErros }}</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaOutrosErros = false">OK</button>
        </div>

        <div v-if="mostrarSucesso" class="filter-download">
            <p class="title-popup">Empresa <strong>{{ empresaNome }}</strong> registrada com sucesso</p>
            <button class="btn-popup" @click.prevent="mostrarSucesso = false">OK</button>
        </div>
        
        <form @submit.prevent="cadastrar()">
            <div class="primeira-linha">
                <input type="text" placeholder="Digite seu CNPJ..." v-model="empresa.cnpj">
                <input type="text" placeholder="Digite o nome da empresa..." v-model="empresa.nome">
            </div>
            <div class="segunda-linha">
                <input type="number" placeholder="Digite seu saldo..." v-model="empresa.saldo" required>
            </div>
            <div class="terceira-linha">
                <input type="decimal" placeholder="Digite sua taxa para depósito..." v-model="empresa.taxaDeposito">
                <input type="decimal" placeholder="Digite sua taxa para saque..." v-model="empresa.taxaSaque">
            </div>

            <button v-if="empresa.cnpj != '' && empresa.nome != '' && empresa.saldo != null  && empresa.taxaDeposito && empresa.taxaSaque" type="submit">Confirmar</button>
        </form>
    </main>
</template>

<script lang="ts">
import axios from 'axios';

export default {
    data() {

        return {

            empresa: {
                cnpj: '',
                nome: '',
                saldo: null,
                taxaDeposito: '',
                taxaSaque: ''
            },
            mostrarAlertaCNPJInvalido: false,
            mostrarSucesso: false,
            empresaNome: '',
            mostrarAlertaConstraint: false,
            mostrarAlertaOutrosErros: false,
            outrosErros: '',
        }
    },

    methods: {

        async cadastrar() {

            this.empresaNome = this.empresa.nome;

            try {

                const response = await axios.post('http://localhost:8080/empresa/registrar-empresa', this.empresa,  {
                        headers: {
                        'Content-Type': 'application/json'
                        }

                });

                this.mostrarSucesso = true;

                this.empresa.cnpj = '';
                this.empresa.nome = '';
                this.empresa.saldo = null;
                this.empresa.taxaDeposito = '';
                this.empresa.taxaSaque = '';

            } catch (error) {
                
                if (error.response.data.mensagem === 'A empresa não pôde ser registrada: CNPJ Inválido') {
                    this.mostrarAlertaCNPJInvalido = true;
                } else if (error.response.data.mensagem.toString().toLowerCase().includes('A empresa não pôde ser registrada: could not execute statement [ERROR: duplicate key value violates unique constraint "empresa_cnpj_key"'.toLowerCase())) {
                    this.mostrarAlertaConstraint = true;
                } else {

                    this.outrosErros = error.response.data.mensagem.toString();

                    this.mostrarAlertaOutrosErros = true;
                }

            }

        },

    },
}
</script>

<style scoped>
main {
    display: flex;
}

button:not(.btn-popup) {
    background-color: var(--verde);
    border-color: var(--cor-contraste-dark);
    padding: 0;
    width: 10rem;
    height: 2.5rem;
}

.btn-popup {
    padding: 0;
    width: 10rem;
    height: 2.5rem;
}

form {
    display: flex;
    flex-direction: column;
    gap: 15px;
    justify-content: center;
    align-items: center;
}

form input {
    padding: 7px;
    margin-left: 25px;
    background-color: var(--cor-contraste-light);
}

.terceira-linha input {
    width: 21rem;
}

h3 {
    background-color: var(--cor-contraste);
    padding: 3rem 25rem;
    border-radius: 5px;
    opacity: 0.6;
}

.filter-download {
    position: fixed;
    background-color: var(--cor-contraste-light);
    border-radius: 10px;
    text-align: center;
    width: 60%;
    padding-top: 10px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    margin-left: 25px;
    margin-right: 25px;
    padding: 40px;
    box-shadow: 2px 2px 20px 5px var(--silver);
    transition: 2s;
    z-index: 9999;
}

.title-popup {

}

.title-popup strong {
    color: var(--laranja-light);
    padding: 5px;
}
</style>