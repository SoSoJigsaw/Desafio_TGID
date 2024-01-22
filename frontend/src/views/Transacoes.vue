<template>
    <main>
        <h1>Qual transação você deseja fazer hoje?</h1>
        <div class="botoes">
            <button @click.prevent="mostrarDeposito()">Depósito</button>
            <button @click.prevent="mostrarSaque()">Saque</button>
        </div>
        
        <div class="transacoes">
            <Deposito v-if="deposito === true" />
            <Saque v-if="saque === true" />
        </div>
    </main>
</template>

<script lang="ts">
import Deposito from '@/components/Deposito.vue';
import Saque from '@/components/Saque.vue';

export default {
    data() {
        return {
            saque: false,
            deposito: false,
        }
    },

    methods: {

        mostrarDeposito() {
            this.saque = false;
            this.deposito = true;
        },

        mostrarSaque() {
            this.deposito = false;
            this.saque = true;
        },

    },

    components: {
        Deposito, Saque
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