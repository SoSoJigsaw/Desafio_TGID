# TransacaoController Endpoints

## <p align="center"> Índices </p>

<div align="center">

|                      Nesse Documento                     |     Documentações de Endpoint     |
|:--------------------------------------------------------:|:---------------------------------:|
|                                                          |  [ClienteController Endpoints](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Documentacao%20de%20EndPoints/ClienteController.md)  |
|                                                          |  [EmpresaController Endpoints](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Documentacao%20de%20EndPoints/EmpresaController.md)  |
|                                                          | [TransacaoController Endpoints](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Documentacao%20de%20EndPoints/TransacaoController.md) |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |
|                                                          |                                   |

</div>

## Endpoint `/transacoes/deposito/{empresaId}/{clienteId}`

- Método do endpoint: `POST`

<br>

### Hipotése 1
- Realiza o depósito com sucesso

#### Request
`http://localhost:8080/transacoes/deposito/3/3`
```json
50.0
```

#### Response
- HTTP STATUS: `200 OK`
```json
Transação de Depósito realizada com sucesso
```

### Hipotése 2
- A tentativa de realizar depósito é mal sucedida, já que o valor é negativo (uma operação de depósito não pode ser com valor negativo)

#### Request
`http://localhost:8080/transacoes/deposito/3/3`
```json
-50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: O valor do depósito não pode ser negativo"
}
```

### Hipotése 3
- A tentativa de realizar depósito é mal sucedida, já que o valor é zero (uma operação de depósito não pode ser zerada)

#### Request
`http://localhost:8080/transacoes/deposito/3/3`
```json
0.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: O valor do depósito não pode ser zero"
}
```

### Hipotése 4
- A tentativa de realizar depósito é mal sucedida, já que o valor não foi informado no request (valor nulo)

#### Request
`http://localhost:8080/transacoes/deposito/3/3`
```json
null
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: Parâmetros numéricos não podem serem nulos. Tente novamente"
}
```

### Hipotése 5
- A tentativa de realizar depósito é mal sucedida, já que o id da empresa informada na operação não se refere a nenhuma empresa do banco de dados (empresa inexistente)

#### Request
`http://localhost:8080/transacoes/deposito/99/3`
```json
50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: A empresa buscada para a operação não existe."
}
```

### Hipotése 6
- A tentativa de realizar depósito é mal sucedida, já que o id do cliente informado na operação não se refere a nenhum cliente do banco de dados (cliente inexistente)

#### Request
`http://localhost:8080/transacoes/deposito/3/99`
```json
50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: O cliente buscado para a operação não existe"
}
```

### Hipotése 7
- A tentativa de realizar depósito é mal sucedida, já que o cliente possui saldo insuficiente para a operação

#### Request
`http://localhost:8080/transacoes/deposito/3/3`
```json
9999999999.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de depósito: {"entidade": "Cliente", "nome": "Pedro Santos", "saldo":2000, "operacao": "Depósito", "valorTransacao": 2147483647}"
}
```


## Endpoint `/transacoes/saque/{empresaId}/{clienteId}`

- Método do endpoint: `POST`

<br>

### Hipotése 1
- Realiza o saque com sucesso

#### Request
`http://localhost:8080/transacoes/saque/3/3`
```json
50.0
```

#### Response
- HTTP STATUS: `200 OK`
```json
Transação de Saque realizada com sucesso
```

### Hipotése 2
- A tentativa de realizar saque é mal sucedida, já que o valor é negativo (uma operação de saque não pode ser com valor negativo)

#### Request
`http://localhost:8080/transacoes/saque/3/3`
```json
-50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: O valor do saque não pode ser negativo"
}
```

### Hipotése 3
- A tentativa de realizar saque é mal sucedida, já que o valor é zero (uma operação de saque não pode ser zerada)

#### Request
`http://localhost:8080/transacoes/saque/3/3`
```json
0.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: O valor do saque não pode ser zero"
}
```

### Hipotése 4
- A tentativa de realizar saque é mal sucedida, já que o valor não foi informado no request (valor nulo)

#### Request
`http://localhost:8080/transacoes/saque/3/3`
```json
null
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: Parâmetros numéricos não podem serem nulos. Tente novamente"
}
```

### Hipotése 5
- A tentativa de realizar saque é mal sucedida, já que o id da empresa informada na operação não se refere a nenhuma empresa do banco de dados (empresa inexistente)

#### Request
`http://localhost:8080/transacoes/saque/99/3`
```json
50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: A empresa buscada para a operação não existe."
}
```

### Hipotése 6
- A tentativa de realizar saque é mal sucedida, já que o id do cliente informado na operação não se refere a nenhum cliente do banco de dados (cliente inexistente)

#### Request
`http://localhost:8080/transacoes/saque/3/99`
```json
50.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: O cliente buscado para a operação não existe"
}
```

### Hipotése 7
- A tentativa de realizar saque é mal sucedida, já que a empresa possui saldo insuficiente para a operação

#### Request
`http://localhost:8080/transacoes/saque/3/3`
```json
9999999999.0
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "Erro no processamento da transação de saque: {"entidade": "Empresa", "nome": "InovaTech Solutions", "saldo": 100000, "operacao": "Saque", "valorTransacao": 2147483647}"
}
```


## Endpoint `/transacoes/listar-transacoes`

- Método do endpoint: `GET`

<br>

### Hipótese 1
- Retorna a lista de todas as transações cadastradas no banco de dados com sucesso

#### Request
`http://localhost:8080/transacoes/listar-transacoes`

#### Response
- HTTP STATUS: `200 OK`
```json
[
  {
    "id": 1,
    "tipo": "DEPÓSITO",
    "valor": 500,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Felipe Sobral",
    "empresaNome": "InovaTech Solutions"
  },
  {
    "id": 2,
    "tipo": "SAQUE",
    "valor": 200,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Maria Oliveira",
    "empresaNome": "Global Innovations"
  },
  {
    "id": 3,
    "tipo": "DEPÓSITO",
    "valor": 800,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Pedro Santos",
    "empresaNome": "InovaTech Solutions"
  },
  {
    "id": 4,
    "tipo": "SAQUE",
    "valor": 300,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Ana Souza",
    "empresaNome": "Future Technologies"
  },
  {
    "id": 5,
    "tipo": "DEPÓSITO",
    "valor": 1000,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Luiz Costa",
    "empresaNome": "ABC Corporation"
  },
  {
    "id": 6,
    "tipo": "SAQUE",
    "valor": 400,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Julia Lima",
    "empresaNome": "MegaSoft Systems"
  },
  {
    "id": 7,
    "tipo": "DEPÓSITO",
    "valor": 600,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Roberto Pereira",
    "empresaNome": "WiseTech Solutions"
  },
  {
    "id": 8,
    "tipo": "SAQUE",
    "valor": 150,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Amanda Alves",
    "empresaNome": "Global Innovations"
  },
  {
    "id": 9,
    "tipo": "DEPÓSITO",
    "valor": 1200,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Lucas Rocha",
    "empresaNome": "Data Dynamics"
  },
  {
    "id": 10,
    "tipo": "SAQUE",
    "valor": 350,
    "dataTransacao": "13/02/24 03:44:56",
    "clienteNome": "Carla Oliveira",
    "empresaNome": "ABC Corporation"
  }
]
```


## Endpoint `/transacoes/delete-transacao/{id}`

- Método do endpoint: `DELETE`

<br>

### Hipótese 1
- Deleta um registro de transação do banco de dados a partir de seu id com sucesso

#### Request
`http://localhost:8080/transacoes/delete-transacao/7`

#### Response
- HTTP STATUS: `200 OK`
```json
Transação deletada com sucesso!
```

### Hipótese 2
- Tentativa de deletar um registro de transação a partir de seu id sem sucesso, pois o id fornecido não se refere a nenhuma transação registrada no banco de dados (a transação não existe)

#### Request
`http://localhost:8080/transacoes/delete-transacao/99`

#### Response
- HTTP STATUS: `404 NOT_FOUND`
```json
{
  "status": "NOT_FOUND",
  "mensagem": "O registro de transação não pôde ser deletado: O registro dessa transação não existe no banco"
}
```