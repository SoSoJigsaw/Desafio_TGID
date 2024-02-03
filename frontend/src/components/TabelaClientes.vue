<template>
    <main>
        <h3>Dados de Clientes</h3>

        <div v-if="confirmarDelete" class="filter-download">
            <p class="title-popup">Tem certeza que deseja excluir o Cliente <strong>{{ clienteNome }}</strong> ?</p>
            <div>
                <button class="btn-popup" id="confirmar" @click.prevent="deletarRegistro(edicaoId)">Confirmar</button>
                <button class="btn-popup" id="cancelar" @click.prevent="confirmarDelete = false">Cancelar</button>
            </div>
        </div>

        <div class="legenda">
            <p class="saldo-positivo"><span>•</span> Saldo positivo</p>
            <p class="saldo-negativo"><span>•</span> Saldo zerado ou negativo</p>
            <input type="text" v-model="searchTerm" placeholder="Filtre aqui...">
        </div>
        <div class="table-wrapper">
            <table cellspacing="0">
                <thead>
                    <tr class="table-header">
                        <th>Id</th>
                        <th>Nome</th>
                        <th>CPF</th>
                        <th>Email</th>
                        <th>Saldo</th>
                        <th>Opções</th>
                    </tr>
                </thead>    
                <tbody>
                    <tr v-if="searchTerm.length === 0" v-for="c in clientes" :key="c.id">
                        <td>{{ c.id }}</td>
                        <td>{{ c.nome }}</td>
                        <td>{{ c.cpf }}</td>
                        <td>{{ c.email }}</td>
                        <td :style="c.saldo > 0 ? 'color: hsla(120, 100%, 25%, 0.9)' : 'color: hsla(0, 100%, 50%, 0.9)'">{{ c.saldo }}</td>
                        <td><button @click.prevent="confirmarDelete = true; edicaoId = c.id; clienteNome = c.nome;">X</button></td>
                    </tr>
                    <tr v-if="searchTerm.length > 0" v-for="c in filtro" :key="c.id">
                        <td>{{ c.id }}</td>
                        <td>{{ c.nome }}</td>
                        <td>{{ c.cpf }}</td>
                        <td>{{ c.email }}</td>
                        <td :style="c.saldo > 0 ? 'color: hsla(120, 100%, 25%, 0.9)' : 'color: hsla(0, 100%, 50%, 0.9)'">{{ c.saldo }}</td>
                        <td><button @click.prevent="confirmarDelete = true; edicaoId = c.id; clienteNome = c.nome;">X</button></td>
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
            clientes: [],
            searchTerm: '',
            confirmarDelete: false,
            edicaoId: '',
            clienteNome: '',
        }
    },

    mounted() {
        
        this.getClientes();

    },

    methods: {

        async getClientes() {

            const response = await axios.get('http://localhost:8080/cliente/listar-clientes');
            this.clientes = response.data.map((cliente: String) => ({ 
                id: cliente.id,
                cpf: cliente.cpf,
                nome: cliente.nome, 
                email: cliente.email,
                saldo: cliente.saldo
            }));
            
        },

        async deletarRegistro(id: number) {

            await axios.delete('http://localhost:8080/cliente/delete-cliente/' + id);

            this.confirmarDelete = false;

            this.getClientes();

        },

    },

    computed: {
        filtro() {
            return this.clientes.filter(cliente => {
        
                for (let propriedade in cliente) {

                    if (cliente[propriedade].toString().toLowerCase().includes(this.searchTerm.toLowerCase())) {
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

.legenda input {
    width: fit-content;
    background-color: var(--cor-contraste-light);
    padding-left: 10px;
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