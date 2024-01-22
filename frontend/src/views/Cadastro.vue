<template>
    <main>
        <h1>O que você quer cadastrar?</h1>
        <div class="botoes">
            <button @click.prevent="cadastrarCliente()">Cadastrar Cliente</button>
            <button @click.prevent="cadastrarEmpresa()">Cadastrar Empresa</button>
        </div>
        
        <div class="cadastro">
            <CadastroCliente v-if="cliente === true"/>
            <CadastroEmpresa v-if="empresa === true"/>
        </div>
    </main>
</template>

<script lang="ts">
import CadastroCliente from '@/components/CadastroCliente.vue';
import CadastroEmpresa from '@/components/CadastroEmpresa.vue';

export default {
    data() {
        return {
            cliente: false,
            empresa: false,
        }
    },

    methods: {

        cadastrarCliente() {
            this.empresa = false;
            this.cliente = true;
        },

        cadastrarEmpresa() {
            this.cliente = false;
            this.empresa = true;
        },

    },

    components: {
        CadastroCliente, CadastroEmpresa
    },
}
</script>

<style scoped>
main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

h1 {
    color: #1F3C92;
}
</style>