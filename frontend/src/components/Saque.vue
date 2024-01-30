<template>
    <main>
        <h1>Sacar</h1>
        <form @submit.prevent="sacar()">
            <select v-model="clienteId">
                <option v-for="cliente in clientes" :key="cliente.id" :value="cliente.id">{{ cliente.nome }}</option>
            </select>
            <select v-model="empresaId">
                <option v-for="empresa in empresas" :key="empresa.id" :value="empresa.id">{{ empresa.nome }}</option>
            </select>
            <input type="number" v-model="valor" placeholder="Digite aqui o valor...">
            <button type="submit">Confirmar</button>
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

            this.empresaId = '';
            this.clienteId = '';
            this.valor = '';
        },

    },
}
</script>

<style scoped>
main {

}

.botoes {

}

button {

}

.cadastro {
    
}

form {

}

select {

}

option {
    
}

h1 {

}
</style>