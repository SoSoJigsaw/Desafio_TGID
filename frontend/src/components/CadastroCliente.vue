<template>
    <main>
        <h1>Cadastro Cliente</h1>
        <form @submit.prevent="cadastrar()">
            <input type="text" placeholder="Digite seu CPF..." v-model="cliente.cpf">
            <input type="text" placeholder="Digite seu nome..." v-model="cliente.nome">
            <input type="email" placeholder="Digite seu email..." v-model="cliente.email">
            <input type="number" placeholder="Digite seu saldo..." v-model="cliente.saldo">
            <button v-if="cliente.cpf != '' && cliente.nome != '' && cliente.email != '' && cliente.saldo != ''" type="submit">Confirmar</button>
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
                saldo: '',
            },
        }
    },

    methods: {

        async cadastrar() {

            await axios.post('http://localhost:8080/registrar-cliente', this.cliente,  {
                    headers: {
                    'Content-Type': 'application/json'
                    }

            });
            
            this.cliente.cpf = '';
            this.cliente.nome = '';
            this.cliente.email = '';
            this.cliente.saldo = '';
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