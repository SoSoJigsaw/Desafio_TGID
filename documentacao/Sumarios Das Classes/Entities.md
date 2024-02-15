# Entities

## <p align="center"> Índices </p>

<div align="center">

|         Nesse Documento          |                                                    Sumários de Classes                                                     |
|:--------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
|   [Cliente.java](#clientejava)   |  [Controllers](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Controllers.md)  |
|   [Empresa.java](#empresajava)   |         [DTOs](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md)          |
| [Transacao.java](#transacaojava) |     [Entities](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Entities.md)     |
|                                  |   [Exceptions](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md)   |
|                                  |        [Infra](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md)        |
|                                  |        [Kafka](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md)        |
|                                  | [Notification](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md) |
|                                  |     [Services](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md)     |
|                                  |         [Util](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Util.md)         |
|                                  |   [Validation](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md)   |

</div>

## Cliente.java

A classe `Cliente` representa uma entidade de cliente em um sistema. Ela contém campos para armazenar as informações do cliente, como CPF (número de identificação brasileiro), nome, e-mail e saldo. Também inclui métodos para obter e definir os valores desses campos.

### Exemplo de Uso

```java
Cliente cliente = new Cliente("12345678901", "John Doe", "johndoe@example.com", 100.0);
System.out.println(cliente.getCpf()); // Saída: 12345678901
System.out.println(cliente.getNome()); // Saída: John Doe
System.out.println(cliente.getEmail()); // Saída: johndoe@example.com
System.out.println(cliente.getSaldo()); // Saída: 100.0
```

### Análise de Código

#### Principais Funcionalidades

- Armazenar e recuperar informações do cliente, como CPF, nome, e-mail e saldo.
- Validar o CPF usando a anotação @CPF.
- Garantir que os campos CPF, nome e e-mail não estejam em branco usando a anotação @NotBlank.
- Garantir que o e-mail esteja em um formato válido usando a anotação @Email.
- Garantir que o saldo não seja nulo usando a anotação @NotNull.

#### Métodos

- `Cliente(String cpf, String nome, String email, Double saldo)`: Um construtor que inicializa o objeto Cliente com o CPF, nome, e-mail e saldo fornecidos.
- `Cliente()`: Um construtor padrão.
- Getters e setters para os campos CPF, nome, e-mail e saldo.

#### Campos

- `id`: Um identificador único para o cliente.
- `cpf`: O CPF (número de identificação brasileiro) do cliente. É um campo único e obrigatório.
- `nome`: O nome do cliente. É um campo obrigatório.
- `email`: O endereço de e-mail do cliente. É um campo obrigatório e deve estar em um formato válido.
- `saldo`: O saldo do cliente. É um campo obrigatório e não deve ser nulo.

## Empresa.java

A classe `Empresa` representa uma entidade de empresa em uma aplicação Java. Ela contém campos para armazenar informações, como o CNPJ (Cadastro Nacional da Pessoa Jurídica) da empresa, nome, saldo, taxa de depósito e taxa de saque. A classe também inclui métodos getter e setter para acessar e modificar esses campos.

### Exemplo de Uso

```java
Empresa empresa = new Empresa();
empresa.setCnpj("04.252.011/0001-10");
empresa.setNome("Test Empresa");
empresa.setSaldo(1000.0);
empresa.setTaxaDeposito(0.1);
empresa.setTaxaSaque(0.2);

assertEquals("04.252.011/0001-10", empresa.getCnpj());
assertEquals("Test Empresa", empresa.getNome());
assertEquals(1000.0, empresa.getSaldo(), 0.001);
assertEquals(0.1, empresa.getTaxaDeposito(), 0.001);
assertEquals(0.2, empresa.getTaxaSaque(), 0.001);
```

### Análise de Código

#### Principais Funcionalidades

- Armazenar e recuperar informações sobre uma empresa, como CNPJ, nome, saldo, taxa de depósito e taxa de saque.
- Fornecer métodos getter e setter para acessar e modificar os campos da empresa.

#### Métodos

- 

#### Campos

- `id`: Representa o identificador único da empresa.
- `cnpj`: Representa o CNPJ (Cadastro Nacional da Pessoa Jurídica) da empresa. É um campo único e não vazio.
- `nome`: Representa o nome da empresa. É um campo não vazio com um comprimento máximo de 100 caracteres.
- `saldo`: Representa o saldo da empresa. É um campo não nulo.
- `taxaDeposito`: Representa a taxa de depósito da empresa. É um campo não nulo com uma escala de 2 casas decimais.
- `taxaSaque`: Representa a taxa de saque da empresa. É um campo não nulo com uma escala de 2 casas decimais.

## Transacao.java

A classe `Transacao` representa uma transação em um sistema financeiro. Ela contém informações como o tipo de transação, o valor, a data e hora da transação, e o cliente e empresa associados.

### Exemplo de Uso

```java
Transacao transacao = new Transacao(100.0, "DEPOSIT", LocalDateTime.now(), cliente, empresa);
```

### Análise do Código

#### Principais Funcionalidades

- Armazenar e gerenciar dados da transação.
- Associar uma transação a um cliente e a uma empresa.

#### Métodos

- `Transacao(double valor, String tipo, LocalDateTime dataTransacao, Cliente cliente, Empresa empresa)`: Construtor que inicializa a transação com os valores fornecidos.
- `Transacao()`: Construtor padrão.

#### Campos

- `id`: O identificador único da transação.
- `tipo`: O tipo da transação (por exemplo, DEPÓSITO, SAQUE).
- `valor`: O valor da transação.
- `dataTransacao`: A data e hora da transação.
- `cliente`: O cliente associado à transação.
- `empresa`: A empresa associada à transação.


<br>
<br>



<p align="left"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md"><<< Anterior: DTOs</a></p>
<p align="right"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md">Próximo: Exceptions >>></a></p>

