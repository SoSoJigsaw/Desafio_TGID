# EmpresaController Endpoints

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

<br>

## Endpoint `/empresa/registrar-empresa`

- Método do endpoint: `POST`



### Hipótese 1
- Registra a empresa com sucesso usando caracteres especiais no CNPJ

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "84.867.778/0001-92",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
Cadastro realizado com sucesso!
```

### Hipótese 2
- Registra a empresa com sucesso sem usar caracteres especiais no CNPJ

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "75045159000121",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
Cadastro realizado com sucesso!
```

### Hipótese 3
- Tentativa sem sucesso de registrar a empresa usando um CNPJ inválido com caracteres especiais

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "84.847.758/0501-92",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: CNPJ Inválido"
}
```

### Hipótese 4
- Tentativa sem sucesso de registrar a empresa usando um CNPJ inválido sem o uso de caracteres especiais

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "75065158050121",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: CNPJ Inválido"
}
```

### Hipótese 5
- Tentativa sem sucesso de registrar empresa por utilizar um CNPJ que já foi utilizado por outra empresa (Violação da Constraint `UNIQUE` atribuída ao CNPJ na lógica do banco de dados), utilizando caracteres especiais no CNPJ fornecido

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "04.270.558/0001-48",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: O CNPJ 04.270.558/0001-48 já foi utilizado por outra empresa. Tente Novamente"
}
```

### Hipótese 6
- Tentativa sem sucesso de registrar empresa por utilizar um CNPJ que já foi utilizado por outra empresa (Violação da Constraint `UNIQUE` atribuída ao CNPJ na lógica do banco de dados), sem utilizar caracteres especiais no CNPJ fornecido

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "68541284000167",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: O CNPJ 68.541.284/0001-67 já foi utilizado por outra empresa. Tente Novamente"
}
```

### Hipótese 7
- Tentativa sem sucesso de registrar empresa sem fornecer um CNPJ (CNPJ nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: CNPJ Inválido"
}
```

### Hipótese 8
- Tentativa sem sucesso de registrar empresa sem fornecer um nome (Nome nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "16.130.469/0001-85",
  "nome": "",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: Os parâmetros de entrada não podem ser nulos"
}
```

### Hipótese 9
- Tentativa sem sucesso de registrar empresa sem fornecer uma Taxa de Depósito (Taxa de Depósito nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "84.227.143/0001-20",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": null,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: Parâmetros numéricos não podem serem nulos. Tente novamente"
}
```

### Hipótese 10
- Tentativa sem sucesso de registrar empresa sem fornecer uma Taxa de Saque (Taxa de Saque nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "76.207.485/0001-50",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": null
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: Parâmetros numéricos não podem serem nulos. Tente novamente"
}
```


### Hipótese 11
- Tentativa sem sucesso de registrar empresa sem fornecer um saldo (Saldo nulo), violando a Constraint de `@NotNull` do atributo no banco de dados e lançando uma exceção `NullPointerException` por se tratar de um atributo do tipo `double`, exceção essa que foi tratada para fornecer uma response mais amigável ao usuário

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "69.143.662/0001-17",
  "nome": "Test",
  "saldo": null,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: Parâmetros numéricos não podem serem nulos. Tente novamente"
}
```

### Hipótese 12
- Tentativa sem sucesso de registrar empresa fornecendo um saldo negativo, lançando uma exceção personalizada, para evitar uma incongruência com a lógica do negócio da aplicação (Os saldos não podem serem menores do que zero)

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "50.691.255/0001-16",
  "nome": "Test",
  "saldo": -5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: O valor do saldo não pode ser negativo. Tente Novamente"
}
```


### Hipótese 13
- Tentativa sem sucesso de registrar empresa fornecendo uma taxa de depósito negativo, lançando uma exceção personalizada, para evitar uma incongruência com a lógica do negócio da aplicação (As taxas de depósito não podem serem menores do que zero)

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "52.378.745/0001-92",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": -0.5,
  "taxaSaque": 0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: O valor da taxa de depósito não pode ser negativo. Tente Novamente"
}
```

### Hipótese 14
- Tentativa sem sucesso de registrar empresa fornecendo uma taxa de saque negativa, lançando uma exceção personalizada, para evitar uma incongruência com a lógica do negócio da aplicação (As taxas de saque não podem serem menores do que zero)

#### Request
`http://localhost:8080/empresa/registrar-empresa`
```json
{
  "cnpj": "65.341.656/0001-13",
  "nome": "Test",
  "saldo": 5000.0,
  "taxaDeposito": 0.5,
  "taxaSaque": -0.5
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A empresa não pôde ser registrada: O valor da taxa de saque não pode ser negativo. Tente Novamente"
}
```


## Endpoint `/empresa/atualizar-taxa`

- Método do endpoint: `POST`



### Hipótese 1
- Atualiza a taxa de saque de uma empresa com sucesso

#### Request
`http://localhost:8080/empresa/atualizar-taxa`
```json
{
  "id": 2,
  "tipoTaxa": "SAQUE",
  "valor": 0.7
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
Taxa associada com sucesso
```

### Hipótese 2
- Atualiza a taxa de depósito de uma empresa com sucesso

#### Request
`http://localhost:8080/empresa/atualizar-taxa`
```json
{
  "id": 2,
  "tipoTaxa": "DEPÓSITO",
  "valor": 0.7
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
Taxa associada com sucesso
```

### Hipótese 3
- Tentativa sem sucesso de atualizar taxa de uma empresa, pelo fato do id fornecido no request não se referir a nenhuma empresa no banco de dados (a empresa não existe)

#### Request
`http://localhost:8080/empresa/atualizar-taxa`
```json
{
  "id": 99,
  "tipoTaxa": "SAQUE",
  "valor": 0.7
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A atualização da taxa falhou: Não há empresa com esse id: 99"
}
```

### Hipótese 4
- Tentativa sem sucesso de atualizar taxa da empresa pelo fato da taxa ser inválida (é aceito como parâmetro apenas as taxas "DEPÓSITO" e "SAQUE")

#### Request
`http://localhost:8080/empresa/atualizar-taxa`
```json
{
  "id": 1,
  "tipoTaxa": "deposito",
  "valor": 0.8
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A atualização da taxa falhou: Tipo de taxa de transação inválida. Valores esperados: DEPÓSITO ou SAQUE"
}
```

### Hipótese 5
- Tentativa sem sucesso de atualizar a taxa da empresa pelo fato do valor da taxa não ter sido fornecida na request (valor de taxa nula), violando a Constraint de `@NotNull` do atributo no banco de dados e lançando uma exceção `NullPointerException` por se tratar de um atributo do tipo `double`, exceção essa que foi tratada para fornecer uma response mais amigável ao usuário

#### Request
`http://localhost:8080/empresa/atualizar-taxa`
```json
{
  "id": 2,
  "tipoTaxa": "SAQUE",
  "valor": null
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "A atualização da taxa falhou: O valor da taxa não pode ser nula. Tente Novamente"
}
```


## Endpoint `/empresa/listar-empresas`

- Método do endpoint: `GET`



### Hipótese 1
- Retorna a lista de todas as empresas cadastradas no banco de dados com sucesso

#### Request
`http://localhost:8080/empresa/listar-empresas`

#### Response
- HTTP STATUS: `200 OK`
```json
[
  {
    "id": 1,
    "cnpj": "21.347.851/0001-85",
    "nome": "Empresa XYZ",
    "saldo": 20000,
    "taxaDeposito": 0.5,
    "taxaSaque": 0.7
  },
  {
    "id": 2,
    "cnpj": "68.541.284/0001-67",
    "nome": "ABC Corporation",
    "saldo": 40000,
    "taxaDeposito": 0.6,
    "taxaSaque": 0.8
  },
  {
    "id": 3,
    "cnpj": "45.199.289/0001-58",
    "nome": "InovaTech Solutions",
    "saldo": 100000,
    "taxaDeposito": 0.7,
    "taxaSaque": 0.9
  },
  {
    "id": 4,
    "cnpj": "32.659.793/0001-41",
    "nome": "TechStart Ltda.",
    "saldo": 0,
    "taxaDeposito": 0.4,
    "taxaSaque": 0.6
  },
  {
    "id": 5,
    "cnpj": "04.270.558/0001-48",
    "nome": "Global Innovations",
    "saldo": 90000,
    "taxaDeposito": 0.6,
    "taxaSaque": 0.8
  },
  {
    "id": 6,
    "cnpj": "25.223.637/0001-78",
    "nome": "MegaSoft Systems",
    "saldo": 45000,
    "taxaDeposito": 0.3,
    "taxaSaque": 0.5
  },
  {
    "id": 7,
    "cnpj": "20.766.943/0001-37",
    "nome": "Data Dynamics",
    "saldo": 65000,
    "taxaDeposito": 0.8,
    "taxaSaque": 1
  },
  {
    "id": 8,
    "cnpj": "10.786.508/0001-10",
    "nome": "Future Technologies",
    "saldo": 80000,
    "taxaDeposito": 0.7,
    "taxaSaque": 0.9
  },
  {
    "id": 9,
    "cnpj": "67.706.847/0001-67",
    "nome": "NexGen Innovations",
    "saldo": 0,
    "taxaDeposito": 0.5,
    "taxaSaque": 0.7
  },
  {
    "id": 10,
    "cnpj": "61.559.248/0001-81",
    "nome": "WiseTech Solutions",
    "saldo": 37500,
    "taxaDeposito": 0.4,
    "taxaSaque": 0.6
  }
]
```


## Endpoint `/empresa/delete-empresa/{id}`

- Método do endpoint: `DELETE`



### Hipótese 1
- Deleta uma empresa do banco de dados a partir de seu id com sucesso

#### Request
`http://localhost:8080/empresa/delete-empresa/1`

#### Response
- HTTP STATUS: `200 OK`
```json
Empresa deletada com sucesso!
```

### Hipótese 2
- Tentativa de deletar uma empresa a partir de seu id sem sucesso, pois o id fornecido não se refere a nenhuma empresa registrada no banco de dados (a empresa não existe)

#### Request
`http://localhost:8080/empresa/delete-empresa/99`

#### Response
- HTTP STATUS: `404 NOT_FOUND`
```json
{
  "status": "NOT_FOUND",
  "mensagem": "A empresa não pôde ser deletada: Não há empresa com esse id: 99"
}
```