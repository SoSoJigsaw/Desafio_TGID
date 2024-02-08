<template>
    <main>
        <h3>Sacar</h3>

        <div v-if="mostrarAlertaSaldoInsuficiente" class="filter-download">
            <p class="title-popup">
                Operação de {{ saldoInsuficiente.operacao }} não pôde ser realizada. O valor excede o
                saldo atual da empresa <strong>{{ saldoInsuficiente.nome }}</strong> de
                <strong>{{ saldoInsuficiente.saldo }}</strong> reais. Tente novamente.
            </p>
            <button class="btn-popup" @click.prevent="mostrarAlertaSaldoInsuficiente = false">OK</button>
        </div>

        <div v-if="mostrarAlertaOutrosErros" class="filter-download">
            <p class="title-popup">{{ outrosErros }}</p>
            <button class="btn-popup" @click.prevent="mostrarAlertaOutrosErros = false">OK</button>
        </div>

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
            mostrarAlertaSaldoInsuficiente: false,
            saldoInsuficiente: {},
            mostrarAlertaOutrosErros: false,
            outrosErros: '',
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

            try {

                const response = await axios.post('http://localhost:8080/transacoes/saque/' + this.empresaId + '/' + this.clienteId, this.valor, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                this.mostrarSucesso = true;

                this.empresaId = '';
                this.clienteId = '';
                this.valor = '';

            } catch (error) {
                
                if (error.response.data.mensagem.toString().toLowerCase().includes('Erro no processamento da transação de saque: {"entidade":'.toLowerCase())) {
                    
                    const jsonStartIndex = error.response.data.mensagem.indexOf('{'); // Encontra o índice do início do JSON
                    const jsonStringWithoutText = error.response.data.mensagem.substring(jsonStartIndex); // Remove o texto antes do JSON
                    const jsonObject = JSON.parse(jsonStringWithoutText); // Faz o parsing da string JSON

                    this.saldoInsuficiente = {
                        entidade: jsonObject.entidade,
                        nome: jsonObject.nome,
                        saldo: jsonObject.saldo,
                        operacao: jsonObject.operacao,
                        valorTransacao: jsonObject.valorTransacao
                    }

                    this.mostrarAlertaSaldoInsuficiente = true;

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

.title-popup strong {
    color: var(--laranja-light);
    padding: 5px;
}
</style>