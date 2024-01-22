<template>
    <main>
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
                    <tr v-for="c in transacoes" :key="c.id">
                        <td>{{ c.id }}</td>
                        <td>{{ c.tipo }}</td>
                        <td>{{ c.valor }}</td>
                        <td>{{ c.dataTransacao }}</td>
                        <td>{{ c.clienteNome }}</td>
                        <td>{{ c.empresaNome }}</td>
                        <td><button @click.prevent="deletarRegistro(c.id)">X</button></td>
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
        }
    },

    mounted() {
        
        this.getTransacoes();

    },

    methods: {

        async getTransacoes() {

            const response = await axios.get('http://localhost:8080/listar-transacoes');
            this.transacoes = response.data.map((t: String) => ({ 
                id: t.id,
                tipo: t.tipo,
                valor: t.valor, 
                dataTransacao: t.dataTransacao,
                clienteNome: t.clienteNome,
                empresaNome: t.empresaNome
            }));
            
        },

        async deletarRegistro(id: String) {

            await axios.get('http://localhost:8080/delete-transacao/' + id);

            this.getTransacoes();

        },

    },
}
</script>

<style scoped>
@import "../assets/base.css";

.table-new-line{

    margin: 0;
    padding: 0;
}
.full-width {
  width: 100%;
  
}

button{
    color: hsla(0, 100%, 50%, 0.9);
    font-size: large;
    font-weight: 600;
    background-color: var(--azul-principal);
    width: 30px;
    height: 30px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.title {
    color: var(--azul-principal);
    margin-left: 25px;
    font-weight: var(--medium);
}

.table-wrapper {
    padding-bottom: 50px;
}

table {
    background-color: white;
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
    background-color: var(--azul-principal);
    border-radius: 15px;
    
}

th {
    padding: 25px;
    color: var(--white);
}

td {
    padding: 10px;
    color: var(--azul-claro);
    text-align: center;
    vertical-align: middle;
}

tr:nth-child(even)    { background-color: rgba(224, 224, 225, 0.5);}

/* --------------- Media Queries -------------------- */

/* Estilos para tablet */
@media only screen and (min-width: 768px) and (max-width: 1023px) {

    .title {
        margin-left: 25px;
    }

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

    .title {
        margin-left: 25px;
    }

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