# ClienteController Endpoints

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

## Endpoint `/cliente/registrar-cliente`

- Método do endpoint: `POST`



### Hipótese 1
- Registrar o cliente com sucesso usando caracteres especiais no CPF

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "624.404.711-78",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
"Cadastro realizado com sucesso!"
```

### Hipótese 2
- Registrar o cliente com sucesso sem usar caracteres especiais no CPF

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "35593186537",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `200 OK`
```json
"Cadastro realizado com sucesso!"
```

### Hipótese 3
- Tentativa sem sucesso de registrar o cliente usando um CPF inválido com caracteres especiais

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "623.404.711-78",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: CPF Inválido"
}
```

### Hipótese 4
- Tentativa sem sucesso de registrar o cliente usando um CPF inválido sem o uso de caracteres especiais

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "62340471178",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: CPF Inválido"
}
```

### Hipótese 5
- Tentativa sem sucesso de registrar cliente por utilizar um CPF que já foi utilizado por outro cliente (Violação da Constraint `UNIQUE` atribuída ao CPF na lógica do banco de dados), utilizando caracteres especiais no CPF fornecido

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "411.012.171-03",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: O CPF 411.012.171-03 já foi utilizado por outro cliente. Tente Novamente"
}
```

### Hipótese 6
- Tentativa sem sucesso de registrar cliente por utilizar um CPF que já foi utilizado por outro cliente (Violação da Constraint `UNIQUE` atribuída ao CPF na lógica do banco de dados), sem utilizar caracteres especiais no CPF fornecido

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "46662505226",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: O CPF 466.625.052-26 já foi utilizado por outro cliente. Tente Novamente"
}
```

### Hipótese 7
- Tentativa sem sucesso de registrar cliente sem fornecer um CPF (CPF nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: CPF Inválido"
}
```

### Hipótese 8
- Tentativa sem sucesso de registrar cliente sem fornecer um nome (Nome nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "686.618.265-43",
  "nome": "",
  "email": "test@test.com",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: Os parâmetros de entrada não podem ser nulos"
}
```

### Hipótese 9
- Tentativa sem sucesso de registrar cliente sem fornecer um email (Email nulo), violando a Constraint de `@NotNull` do atributo

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "824.443.078-87",
  "nome": "Test",
  "email": "",
  "saldo": 100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: Os parâmetros de entrada não podem ser nulos"
}
```

### Hipótese 10
- Tentativa sem sucesso de registrar cliente sem fornecer um saldo (Saldo nulo), violando a Constraint de `@NotNull` do atributo no banco de dados e lançando uma exceção `NullPointerException` por se tratar de um atributo do tipo `double`, exceção essa que foi tratada para fornecer uma response mais amigável ao usuário

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "312.665.837-77",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": null
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: Parâmetros numéricos não podem ser nulos. Tente novamente"
}
```

### Hipótese 11
- Tentativa sem sucesso de registrar cliente sem fornecendo um saldo negativo, lançando uma exceção personalizada, para evitar uma incongruência com a lógica do negócio da aplicação (Os saldos não podem serem menores do que zero)

#### Request
`http://localhost:8080/cliente/registrar-cliente`
```json
{
  "cpf": "781.385.118-85",
  "nome": "Test",
  "email": "test@test.com",
  "saldo": -100.0
}
```

#### Response
- HTTP STATUS: `400 BAD_REQUEST`
```json
{
  "status": "BAD_REQUEST",
  "mensagem": "O cliente não pôde ser registrado: O valor do saldo não pode ser negativo. Tente Novamente"
}
```


## Endpoint `/cliente/listar-clientes`

- Método do endpoint: `GET`



### Hipótese 1
- Retorna a lista de todos os clientes cadastrados no banco de dados com sucesso

#### Request
`http://localhost:8080/cliente/listar-clientes`

#### Response
- HTTP STATUS: `200 OK`
```json
[
	{
		"id": 1,
		"cpf": "857.329.142-77",
		"nome": "Felipe Sobral",
		"email": "felipesobral_@hotmail.com",
		"saldo": 1000
	},
	{
		"id": 2,
		"cpf": "411.012.171-03",
		"nome": "Maria Oliveira",
		"email": "maria-oliveira@email.com",
		"saldo": 1500
	},
	{
		"id": 3,
		"cpf": "466.625.052-26",
		"nome": "Pedro Santos",
		"email": "pedro.santos@email.com",
		"saldo": 2000
	},
	{
		"id": 4,
		"cpf": "558.687.551-97",
		"nome": "Ana Souza",
		"email": "ana.souza@email.com",
		"saldo": 1200
	},
	{
		"id": 5,
		"cpf": "852.915.865-24",
		"nome": "Luiz Costa",
		"email": "luiz.costa@email.com",
		"saldo": 0
	},
	{
		"id": 6,
		"cpf": "808.889.643-66",
		"nome": "Julia Lima",
		"email": "julia.lima@email.com",
		"saldo": 900
	},
	{
		"id": 7,
		"cpf": "037.012.245-36",
		"nome": "Roberto Pereira",
		"email": "roberto.pereira@email.com",
		"saldo": 0
	},
	{
		"id": 8,
		"cpf": "111.113.653-00",
		"nome": "Amanda Alves",
		"email": "amanda.alves@email.com",
		"saldo": 1600
	},
	{
		"id": 9,
		"cpf": "146.831.947-75",
		"nome": "Lucas Rocha",
		"email": "lucas.rocha@email.com",
		"saldo": 2200
	},
	{
		"id": 10,
		"cpf": "053.164.618-19",
		"nome": "Carla Oliveira",
		"email": "carla.oliveira@email.com",
		"saldo": 0
	}
]
```


## Endpoint `/cliente/delete-cliente/{id}`

- Método do endpoint: `DELETE`



### Hipótese 1
- Deleta um cliente do banco de dados a partir de seu id com sucesso

#### Request
`http://localhost:8080/cliente/delete-cliente/1`

#### Response
- HTTP STATUS: `200 OK`
```json
"Cliente deletado com sucesso!"
```

### Hipótese 2
- Tentativa de deletar um cliente a partir de seu id sem sucesso, pois o id fornecido não se refere a nenhum cliente registrado no banco de dados (o cliente não existe)

#### Request
`http://localhost:8080/cliente/delete-cliente/99`

#### Response
- HTTP STATUS: `404 NOT_FOUND`
```json
{
  "status": "NOT_FOUND",
  "mensagem": "O cliente não pôde ser deletado: Não há cliente com esse id: 99"
}
```


