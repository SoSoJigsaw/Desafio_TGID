<template>
    <main>
        <h3>Dados de Transações</h3>

        <div v-if="confirmarDelete" class="filter-download">
            <p class="title-popup">Tem certeza que deseja excluir este registro de transação?</p>
            <div>
                <button class="btn-popup" id="confirmar" @click.prevent="deletarRegistro(edicaoId)">Confirmar</button>
                <button class="btn-popup" id="cancelar" @click.prevent="confirmarDelete = false">Cancelar</button>
            </div>
        </div>

        <div class="legenda">
            <p class="deposito"><span>•</span> Depósito <input type="checkbox" v-model="mostrarDeposito" @change="checkBoxHandler"></p>
            <p class="saque"><span>•</span> Saque <input type="checkbox" v-model="mostrarSaque" @change="checkBoxHandler"></p>
            <input type="text" v-model="searchTerm" placeholder="Filtre aqui...">
        </div>

        <div class="table-wrapper">
            <table cellspacing="0">
                <thead>
                    <tr class="table-header">
                        <th>Id</th>
                        <th>Tipo</th>
                        <th>Valor</th>
                        <th>Data da Transação</th>
                        <th>Nome do Cliente</th>
                        <th>Nome da Empresa</th>
                        <th>Opções</th>
                    </tr>
                </thead>    
                <tbody>
                    <tr v-if="searchTerm.length === 0" v-for="c in transacoes" :key="c.id">
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.id }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.tipo }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">R$ {{ c.valor }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.dataTransacao }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.clienteNome }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.empresaNome }}</td>
                        <td><button @click.prevent="confirmarDelete = true; edicaoId = c.id;">X</button></td>
                    </tr>
                    <tr v-if="searchTerm.length > 0" v-for="c in filtro" :key="c.id">
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.id }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.tipo }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">R$ {{ c.valor }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.dataTransacao }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.clienteNome }}</td>
                        <td :style="c.tipo == 'DEPÓSITO' ? 'color: #F29F80' : 'color: #F26363'">{{ c.empresaNome }}</td>
                        <td><button @click.prevent="confirmarDelete = true; edicaoId = c.id;">X</button></td>
                    </tr>
                </tbody>
            </table>   
        </div>     
    </main>
</template>

<script lang="ts">
import axios from 'axios';

export default {
    data() {
        return {
            transacoes: [],
            searchTerm: '',
            confirmarDelete: false,
            edicaoId: '',
            mostrarDeposito: true,
            mostrarSaque: true,
        }
    },

    mounted() {
        
        this.getTransacoes();

    },

    methods: {

        async getTransacoes() {

            const response = await axios.get('http://localhost:8080/transacoes/listar-transacoes');
            this.transacoes = response.data.map((t: String) => ({ 
                id: t.id,
                tipo: t.tipo,
                valor: t.valor, 
                dataTransacao: t.dataTransacao,
                clienteNome: t.clienteNome,
                empresaNome: t.empresaNome
            }));

            for (let i = 0; i < this.transacoes.length; i++) {
                
                let valor = parseInt(this.transacoes[i].valor);
                
                this.transacoes[i].valor = valor; 
            }

            if (this.mostrarDeposito && !this.mostrarSaque) {
                this.transacoes = this.transacoes.filter(transacao => transacao.tipo === 'DEPÓSITO');    
            }

            if (!this.mostrarDeposito && this.mostrarSaque) {
                this.transacoes = this.transacoes.filter(transacao => transacao.tipo === 'SAQUE')
            }

            if (!this.mostrarDeposito && !this.mostrarSaque) {
                this.transacoes = [];
            }

            this.transacoes.sort((a, b) => a.id - b.id);
            
        },

        async deletarRegistro(id: String) {

            await axios.delete('http://localhost:8080/transacoes/delete-transacao/' + id);

            this.confirmarDelete = false;

            this.getTransacoes();

        },

        checkBoxHandler() {
            this.getTransacoes();
        },

    },

    computed: {
        filtro() {
            return this.transacoes.filter(transacao => {
        
                for (let propriedade in transacao) {

                    if (transacao[propriedade].toString().toLowerCase().includes(this.searchTerm.toLowerCase())) {
                        return true; 
                    }
                }
            return false; 
            });
        },            
    },
}
</script>

<style scoped>
@import "../assets/base.css";

h3 {
    background-color: var(--cor-contraste);
    padding: 3rem 25rem;
    border-radius: 5px;
    opacity: 0.6;
}

.legenda {
    display: flex;
    flex-direction: row;
    gap: 45px;
    justify-content: center;
    align-items: center;
}

.legenda input:not(.legenda p input) {
    width: fit-content;
    background-color: var(--cor-contraste-light);
    padding-left: 10px;
}

.legenda p {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: baseline;
    gap: 5px;
}

.legenda p input {
    width: 20px;
    justify-content: center;
    align-self: flex-end;
    justify-content: flex-end;
}

p span {
    font-size: 30px;
    font-weight: 800;
}

.deposito span {
    color: #F29F80;
}

.saque span {
    color: #F26363;
}

button:not(.btn-popup) {
    color: hsla(0, 100%, 50%, 0.9);
    font-size: large;
    font-weight: 600;
    background-color: var(--cor-principal);
    width: 30px;
    height: 30px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.btn-popup {
    padding: 0;
    width: 10rem;
    height: 2.5rem;
}

#cancelar {
    background-color: hsla(0, 100%, 50%, 0.6);
    border-color: hsla(0, 100%, 50%, 0.9);
}

.table-wrapper {
    padding-bottom: 50px;
}

table {
    background-color: var(--cor-contraste-light);
    border-radius: 15px;
    border-collapse: separate;
    margin-left: 25px;
    margin-right: 25px;
    margin-top: 20px;
    margin-bottom: 30px;
    overflow: hidden;
    box-shadow: 2px 2px 20px 5px var(--silver);
    width: 96%;
    
}

.table-header {
    background-color: var(--verde);
    border-radius: 15px;
    
}

th {
    padding: 25px;
    color: white;
}

td {
    padding: 20px;
    color: black;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
}

tr:nth-child(even)    { background-color: rgba(224, 224, 225, 0.5);}

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

/* --------------- Media Queries -------------------- */

/* Estilos para tablet */
@media only screen and (min-width: 768px) and (max-width: 1023px) {

    .table-wrapper {
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
        max-width: 100vw;
        padding-bottom: 50px;
    }

    .table-wrapper::-webkit-scrollbar {
        display: none;
    }    

    table {
        margin-left: 25px;
        margin-right: 25px;
        margin-top: 20px;
        margin-bottom: 30px;
        box-shadow: 2px 2px 20px 5px var(--silver);
        width: 93%;
        
    }

    th {
        padding: 25px;
    }

    td {
        padding: 10px;
    }

}

/* Estilos para mobile */
@media only screen and (max-width: 767px) {

    .table-wrapper {
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
        max-width: 100vw;
        padding-bottom: 80px;
    }

    .table-wrapper::-webkit-scrollbar {
        display: none;
    }    

    table {
        margin-left: 15px;
        margin-right: 15px;
        margin-top: 20px;
        margin-bottom: 30px;
        box-shadow: 2px 2px 20px 5px var(--silver);
        width: 96%;
    }

    th {
        padding: 25px;
    }

    td {
        padding: 10px;
    }

}     
</style>