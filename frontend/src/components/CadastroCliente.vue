<template>
    <main>
        <h3>Cadastro Cliente</h3>

        <div v-if="mostrarAlertaCPFInvalido" class="filter-download">
            <p class="title-popup">CPF Inválido! Revise e tente novamente</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaCPFInvalido = false">OK</button>
        </div>

        <div v-if="mostrarAlertaOutrosErros" class="filter-download">
            <p class="title-popup">{{ outrosErros }}</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaOutrosErros = false">OK</button>
        </div>

        <div v-if="mostrarSucesso" class="filter-download">
            <p class="title-popup">Cliente <strong>{{ clienteNome }}</strong> registrado com sucesso</p>
            <button class="btn-popup" @click.prevent="mostrarSucesso = false">OK</button>
        </div>

        <form @submit.prevent="cadastrar()">
            <div class="primeira-linha">
                <input type="text" placeholder="Digite seu CPF..." v-model="cliente.cpf" @change="formataCpf" required>
                <input type="text" placeholder="Digite seu nome..." v-model="cliente.nome" required>
            </div>
            <div class="segunda-linha">
                <input type="email" placeholder="Digite seu email..." v-model="cliente.email" required>
                <input type="number" min="0" step="0.10" placeholder="Digite seu saldo..." v-model="cliente.saldo" required>
            </div>

            <button v-if="cliente.cpf != '' && cliente.nome != '' && cliente.email != '' && cliente.saldo != null" type="submit">Confirmar</button>
        </form>
    </main>
</template>

<script lang="ts">
import axios from 'axios';

export default {
    data() {

        return {

            cliente: {
                cpf: '',
                nome: '',
                email: '',
                saldo: null,
            },
            mostrarAlertaCPFInvalido: false,
            mostrarSucesso : false,
            clienteNome: '',
            mostrarAlertaOutrosErros: false,
            outrosErros: '',
        }
    },

    methods: {

        async cadastrar() {

            this.clienteNome = this.cliente.nome;

            try {

                const response = await axios.post('http://localhost:8080/cliente/registrar-cliente', this.cliente,  {
                        headers: {
                        'Content-Type': 'application/json'
                        }

                });

                this.mostrarSucesso = true;
                
                this.cliente.cpf = '';
                this.cliente.nome = '';
                this.cliente.email = '';
                this.cliente.saldo = null;

            } catch (error) {

                if (error.response.data.mensagem === 'O cliente não pôde ser registrado: CPF Inválido') {
                    this.mostrarAlertaCPFInvalido = true;
                
                } else {

                    this.outrosErros = error.response.data.mensagem.toString();

                    this.mostrarAlertaOutrosErros = true;
                }
                        
            }
        },

            formataCpf() {
                    // Remove todos os caracteres não numéricos
                    let cpf = this.cliente.cpf.replace(/\D/g, '');
                    // Adiciona a formatação do CPF
                    cpf = cpf.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, '$1.$2.$3-$4');
                    // Atualiza o valor do campo
                    this.cliente.cpf = cpf;
                }
            },
                
}
</script>

<style scoped>
@import "../assets/base.css";

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