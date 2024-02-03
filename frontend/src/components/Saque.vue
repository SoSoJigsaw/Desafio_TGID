<template>
    <main>
        <h3>Sacar</h3>

        <div v-if="mostrarSucesso" class="filter-download">
            <p class="title-popup">Saque realizado com sucesso</p>
            <button class="btn-popup" @click.prevent="mostrarSucesso = false">OK</button>
        </div>

        <form @submit.prevent="sacar()">
            <div class="primeira-linha">
                <select v-model="clienteId">
                    <option :value="''">Escolha o cliente...</option>
                    <option v-for="cliente in clientes" :key="cliente.id" :value="cliente.id">{{ cliente.nome }}</option>
                </select>
                <select v-model="empresaId">
                    <option :value="''">Escolha a empresa...</option>
                    <option v-for="empresa in empresas" :key="empresa.id" :value="empresa.id">{{ empresa.nome }}</option>
                </select>
            </div>    
            <div class="segunda-linha">
                <input type="number" v-model="valor" placeholder="Digite aqui o valor...">
            </div>

            <button v-if="clienteId != '' && empresaId != '' && valor != ''" type="submit">Confirmar</button>
        </form>
    </main>
</template>

<script lang="ts">
import axios from 'axios';

export default {
    data() {
        return {
            empresaId: '',
            clienteId: '',
            valor: '',
            empresas: [],
            clientes: [],
            mostrarSucesso: false,
        }
    },

    mounted() {
        
        this.getClientes();
        this.getEmpresas();

    },

    methods: {

        async getClientes() {

            const response = await axios.get('http://localhost:8080/cliente/listar-clientes');
            this.clientes = response.data.map((cliente: String) => ({ 
                id: cliente.id,
                nome: cliente.nome, 
            }));
            
        },

        async getEmpresas() {

            const response = await axios.get('http://localhost:8080/empresa/listar-empresas');
            this.empresas = response.data.map((empresa: String) => ({ 
                id: empresa.id,
                nome: empresa.nome, 
            }));

        },

        async sacar() {

            await axios.post('http://localhost:8080/transacoes/saque/' + this.empresaId + '/' + this.clienteId, this.valor, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            this.mostrarSucesso = true;

            this.empresaId = '';
            this.clienteId = '';
            this.valor = '';
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
    padding: 5px;   
    background-color: var(--cor-contraste-light);
}

form select {
    padding: 3px;
    margin-left: 15px;
    background-color: var(--cor-contraste-light);
}

form select option {
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
</style>