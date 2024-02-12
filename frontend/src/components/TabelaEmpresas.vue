<template>
    <main>
        <h3>Dados de Empresas</h3>

        <div v-if="confirmarDelete" class="filter-download">
            <p class="title-popup">Tem certeza que deseja excluir a Empresa <strong>{{ empresaNome }}</strong> ?</p>
            <div>
                <button class="btn-popup" id="confirmar" @click.prevent="deletarRegistro(edicaoId)">Confirmar</button>
                <button class="btn-popup" id="cancelar" @click.prevent="confirmarDelete = false">Cancelar</button>
            </div>
        </div>

        <div v-if="alertaDeErro" class="filter-download">
            <p class="title-popup">{{ outrosErros }}</p>
            <button class="btn-popup" @click.prevent="alertaDeErro = false">OK</button>
        </div>

        <div v-if="mostrarSucesso" class="filter-download">
            <p class="title-popup">Alteração de taxa <strong>{{ taxaNome }}</strong> realizada com sucesso para <strong>{{ taxaValor }}</strong></p>
            <button class="btn-popup" @click.prevent="mostrarSucesso = false">OK</button>
        </div>

        <div class="legenda">
            <p class="saldo-positivo"><span>•</span> Saldo positivo <input type="checkbox" v-model="mostrarPositivo" @change="checkBoxHandler"></p>
            <p class="saldo-negativo"><span>•</span> Saldo zerado <input type="checkbox" v-model="mostrarNegativo" @change="checkBoxHandler"></p>
            <input type="text" v-model="searchTerm" placeholder="Filtre aqui...">
        </div>
        
        <div class="table-wrapper">
            <table cellspacing="0">
                <thead>
                    <tr class="table-header">
                        <th>Id</th>
                        <th>Nome</th>
                        <th>CNPJ</th>
                        <th>Saldo</th>
                        <th>Taxa de Depósito</th>
                        <th>Taxa de Saque</th>
                        <th>Opções</th>
                    </tr>
                </thead>    
                <tbody>
                    <tr v-if="searchTerm.length === 0" v-for="c in empresas" :key="c.id">
                        <td>{{ c.id }}</td>
                        <td>{{ c.nome }}</td>
                        <td>{{ c.cnpj }}</td>
                        <td :style="c.saldo > 0 ? 'color: hsla(120, 100%, 25%, 0.9)' : 'color: hsla(0, 100%, 50%, 0.9)'" class="saldo"><p v-if="c.saldo != 'Sem Saldo'">R$ </p>{{ c.saldo }}</td>
                        <td v-if="editTaxaDeposito !== c.id">
                            {{ c.taxaDeposito }}
                            <button class="button-edit" @click.prevent="editTaxaDeposito = c.id">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                        </td>
                        <td class="edit-taxa" v-else-if="editTaxaDeposito == c.id">
                            <form @submit.prevent="taxaDeposito != null ? editarTaxa(c.id, 'DEPÓSITO', taxaDeposito) : '';">
                                <input type="number" min="0.10" max="0.99" step=".01" :placeholder="c.taxaDeposito" v-model="taxaDeposito" required>
                                <button class="button-confirm" type="submit">✔</button>
                                <button class="button-delete" @click.prevent="editTaxaDeposito = ''; taxaDeposito = '';">X</button>
                            </form>    
                        </td>
                        <td v-if="editTaxaSaque !== c.id">
                            {{ c.taxaSaque }}
                            <button class="button-edit" @click.prevent="editTaxaSaque = c.id">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                        </td>
                        <td class="edit-taxa" v-else-if="editTaxaSaque == c.id">
                            <form @submit.prevent="taxaSaque != null ? editarTaxa(c.id, 'SAQUE', taxaSaque) : ''">
                                <input type="number" min="0.10" max="0.99" step=".01" :placeholder="c.taxaSaque" v-model="taxaSaque" required>
                                <button class="button-confirm" type="submit">✔</button>                             
                                <button class="button-delete" @click.prevent="editTaxaSaque = ''; taxaSaque = '';">X</button>
                            </form>
                        </td>
                        <td><button class="button-delete" @click.prevent="confirmarDelete = true; edicaoId = c.id; empresaNome = c.nome">X</button></td>
                    </tr>
                    <tr v-if="searchTerm.length > 0" v-for="c in filtro" :key="c.id">
                        <td>{{ c.id }}</td>
                        <td>{{ c.nome }}</td>
                        <td>{{ c.cnpj }}</td>
                        <td :style="c.saldo > 0 ? 'color: hsla(120, 100%, 25%, 0.9)' : 'color: hsla(0, 100%, 50%, 0.9)'" class="saldo"><p v-if="c.saldo != 'Sem Saldo'">R$ </p>{{ c.saldo }}</td>
                        <td v-if="editTaxaDeposito !== c.id">
                            {{ c.taxaDeposito }}
                            <button class="button-edit" @click.prevent="editTaxaDeposito = c.id">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                        </td>
                        <td class="edit-taxa" v-else-if="editTaxaDeposito == c.id">
                            <form @submit.prevent="taxaDeposito != null ? editarTaxa(c.id, 'DEPÓSITO', taxaDeposito) : '';">
                                <input type="number" min="0.10" max="0.99" step=".01" :placeholder="c.taxaDeposito" v-model="taxaDeposito" required>
                                <button class="button-confirm" type="submit">✔</button>
                                <button class="button-delete" @click.prevent="editTaxaDeposito = ''; taxaDeposito = '';">X</button>
                            </form>    
                        </td>
                        <td v-if="editTaxaSaque !== c.id">
                            {{ c.taxaSaque }}
                            <button class="button-edit" @click.prevent="editTaxaSaque = c.id">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                        </td>
                        <td class="edit-taxa" v-else-if="editTaxaSaque == c.id">
                            <form @submit.prevent="taxaSaque != null ? editarTaxa(c.id, 'SAQUE', taxaSaque) : ''">
                                <input type="number" min="0.10" max="0.99" step=".01" :placeholder="c.taxaSaque" v-model="taxaSaque" required>
                                <button class="button-confirm" type="submit">✔</button>                             
                                <button class="button-delete" @click.prevent="editTaxaSaque = false; taxaSaque = '';">X</button>
                            </form>
                        </td>
                        <td><button class="button-delete" @click.prevent="confirmarDelete = true; edicaoId = c.id; empresaNome = c.nome">X</button></td>
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
            empresas: [],
            editTaxaDeposito: '',
            editTaxaSaque: '',
            taxaSaque: '',
            taxaDeposito: '',
            taxaDTO: {},
            searchTerm: '',
            mostrarSucesso: false,
            taxaNome: '',
            taxaValor: null,
            confirmarDelete: false,
            edicaoId: '',
            empresaNome: '',
            mostrarPositivo: true,
            mostrarNegativo: true,
            alertaDeErro: false,
            outrosErros: '',
        }
    },

    mounted() {
        
        this.getEmpresas();

    },

    methods: {

        async getEmpresas() {

            try {
                const response = await axios.get('http://localhost:8080/empresa/listar-empresas');
                this.empresas = response.data.map((e: String) => ({ 
                    id: e.id,
                    cnpj: e.cnpj,
                    nome: e.nome, 
                    saldo: e.saldo,
                    taxaDeposito: e.taxaDeposito,
                    taxaSaque: e.taxaSaque
                }));

                for (let i = 0; i < this.empresas.length; i++) {
                    
                    let saldo = parseInt(this.empresas[i].saldo);
                    
                    this.empresas[i].saldo = saldo; 
                }

                for (let i = 0; i < this.empresas.length; i++) {
                    let saldo = parseInt(this.empresas[i].saldo);
                    if (saldo === 0 || saldo === 0.0 || saldo === 0.00) {
                        this.empresas[i].saldo = 'Sem Saldo';
                    }
                }

                if (this.mostrarPositivo && !this.mostrarNegativo) {
                    this.empresas = this.empresas.filter(empresa => parseInt(empresa.saldo) > 0);    
                }

                if (!this.mostrarPositivo && this.mostrarNegativo) {
                    this.empresas = this.empresas.filter(empresa => empresa.saldo === 'Sem Saldo')
                }

                if (!this.mostrarPositivo && !this.mostrarNegativo) {
                    this.empresas = [];
                }

                this.empresas.sort((a, b) => a.id - b.id);
            
            } catch (error) {

                this.outrosErros = error.response.data.mensagem.toString();

                this.alertaDeErro = true;

            }
        },

        async deletarRegistro(id: String) {

            try {

                await axios.delete('http://localhost:8080/empresa/delete-empresa/' + id);
            
                this.confirmarDelete = false;

                this.getEmpresas();

            } catch (error) {

                this.outrosErros = error.response.data.mensagem.toString();

                this.alertaDeErro = true;

            } 

        },

        async editarTaxa(id: String, tipoTaxa: string, valor: any) {

            try {

                this.taxaNome = tipoTaxa;
                this.taxaValor = valor;

                this.taxaDTO = {
                    id: id,
                    tipoTaxa: tipoTaxa,
                    valor: valor
                };

                await axios.post('http://localhost:8080/empresa/atualizar-taxa', this.taxaDTO, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                });

                this.mostrarSucesso = true;

                this.getEmpresas();

                if (tipoTaxa === 'DEPÓSITO') {
                    this.taxaDeposito = '';
                    this.editTaxaDeposito = '';
                }
                
                if (tipoTaxa === 'SAQUE') {
                    this.taxaSaque = '';
                    this.editTaxaSaque = '';
                }
                
                this.taxaDTO = {};

            } catch (error) {

                this.outrosErros = error.response.data.mensagem.toString();

                this.alertaDeErro = true;

            }
            
        },

        checkBoxHandler() {
            this.getEmpresas();
        },

    },

    computed: {
        filtro() {
            return this.empresas.filter(empresa => {
        
                for (let propriedade in empresa) {

                    if (empresa[propriedade].toString().toLowerCase().includes(this.searchTerm.toLowerCase())) {
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

.saldo-positivo span {
    color: hsla(120, 100%, 25%, 0.9);
}

.saldo-negativo span {
    color: hsla(0, 100%, 50%, 0.9);
}

button:not(.btn-popup) {
    font-size: large;
    font-weight: 600;
    background-color: var(--cor-principal);
    width: 30px;
    height: 30px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.button-delete {
    color: hsla(0, 100%, 50%, 0.9);
}

.edit-taxa {
    white-space: nowrap;
}

.edit-taxa form {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.button-edit {
    color: hsla(120, 100%, 25%, 0.9);
}

.button-edit i {
    font-weight: 800;
}

.button-confirm {
    color: hsla(240, 100%, 50%, 0.9);
    font-weight: 800;
}

input {
    padding: 0;
    padding-left: 7px;
    background-color: var(--cor-contraste-light);
    width: 4rem;
    margin: 0;
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
    padding: 20px;
    color: white;
}

td {
    padding: 20px;
    color: black;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
}

.saldo {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    gap: 7px;
}

tr:nth-child(even)    { background-color: rgba(224, 224, 225, 0.6);}

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