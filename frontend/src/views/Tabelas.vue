<template>
    <main>
        <h1>Quais dados você quer acessar?</h1>
        <div class="botoes">
            <button @click.prevent="mostrarClientes()">Clientes</button>
            <button @click.prevent="mostrarEmpresas()">Empresas</button>
            <button @click.prevent="mostrarTransacoes()">Transações</button>
        </div>
        
        <div class="tabelas">
            <TabelaClientes v-if="clientes === true" />
            <TabelaEmpresas v-if="empresas === true" />
            <TabelaTransacoes v-if="transacoes === true" />
        </div>
    </main>
</template>

<script lang="ts">
import TabelaClientes from '@/components/TabelaClientes.vue';
import TabelaEmpresas from '@/components/TabelaEmpresas.vue';
import TabelaTransacoes from '@/components/TabelaTransacoes.vue';

export default {
    data() {
        return {
            clientes: false,
            empresas: false,
            transacoes: false,
        }
    },

    methods: {

        mostrarClientes() {
            this.empresas = false;
            this.transacoes = false;
            this.clientes = true;
        },

        mostrarEmpresas() {
            this.clientes = false;
            this.transacoes = false;
            this.empresas = true;
        },

        mostrarTransacoes() {
            this.clientes = false;
            this.empresas = false;
            this.transacoes = true;
        },

    },

    components: {
        TabelaClientes, TabelaEmpresas, TabelaTransacoes
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