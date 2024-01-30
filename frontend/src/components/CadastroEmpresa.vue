<template>
    <main>
        <h1>Cadastro Empresa</h1>
        <form @submit.prevent="cadastrar()">
            <input type="text" placeholder="Digite seu CNPJ..." v-model="empresa.cnpj">
            <input type="text" placeholder="Digite seu nome..." v-model="empresa.nome">
            <input type="number" placeholder="Digite seu saldo..." v-model="empresa.saldo">
            <input type="decimal" placeholder="Digite sua taxa para depósito..." v-model="empresa.taxaDeposito">
            <input type="decimal" placeholder="Digite sua taxa para saque..." v-model="empresa.taxaSaque">
            <button v-if="empresa.cnpj != '' && empresa.nome != '' && empresa.saldo != '' && empresa.taxaDeposito && empresa.taxaSaque" type="submit">Confirmar</button>
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
                saldo: '',
                taxaDeposito: '',
                taxaSaque: ''
            },
        }
    },

    methods: {

        async cadastrar() {

            await axios.post('http://localhost:8080/empresa/registrar-empresa', this.empresa,  {
                    headers: {
                    'Content-Type': 'application/json'
                    }

            });
            
            this.empresa.cnpj = '';
            this.empresa.nome = '';
            this.empresa.saldo = '';
            this.empresa.taxaDeposito = '';
            this.empresa.taxaSaque = '';
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