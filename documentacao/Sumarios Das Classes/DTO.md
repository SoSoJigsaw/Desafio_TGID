# DTOs

## CallbackDTO.class

A classe `CallbackDTO` é uma classe simples em Java que representa um objeto de retorno com uma URL e uma mensagem.

### Exemplo de Uso

```java
CallbackDTO callback = new CallbackDTO("https://example.com/callback", "Mensagem Teste");
String url = callback.getUrl(); // "https://example.com/callback"
String message = callback.getMensagem(); // "Mensagem Teste"
```

### Análise de Código

#### Principais Funcionalidades

- A principal funcionalidade da classe CallbackDTO é fornecer uma estrutura de dados para armazenar uma URL de retorno e uma mensagem.

#### Métodos

- A classe CallbackDTO não possui nenhum método.

#### Campos

- `url`: um campo de string que representa a URL de retorno.
- `mensagem`: um campo de string que representa a mensagem de retorno.

## ClienteDTO.class

A classe `ClienteDTO` é um objeto de transferência de dados que representa um cliente com suas informações, como ID, CPF, nome, email e saldo.

### Exemplo de Uso

```java
// Criando uma nova instância de ClienteDTO
ClienteDTO cliente = new ClienteDTO();
cliente.setId(1);
cliente.setCpf("12345678901");
cliente.setNome("John Doe");
cliente.setEmail("johndoe@example.com");
cliente.setSaldo(100.0);
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe ClienteDTO é manter e transferir dados do cliente entre diferentes camadas de uma aplicação. Ela fornece métodos getter e setter para acessar e modificar as informações do cliente.

#### Métodos

A classe ClienteDTO não possui nenhum método adicional. Ela apenas fornece getters e setters para os campos.

#### Campos

- `id`: Representa o identificador único do cliente.
- `cpf`: Representa o CPF (Cadastro de Pessoas Físicas) do cliente.
- `nome`: Representa o nome do cliente.
- `email`: Representa o endereço de email do cliente.
- `saldo`: Representa o saldo ou montante de dinheiro associado ao cliente.

## EmailDTO.class

A classe `EmailDTO` é uma classe simples em Java que representa uma mensagem de e-mail. Ela possui três campos: destinatario (destinatário), assunto e corpo. A classe fornece métodos getter e setter para esses campos, além de construtores para criar instâncias da classe com diferentes combinações de valores dos campos.

### Exemplo de Uso

```java
EmailDTO email = new EmailDTO("test@example.com", "Assunto Teste", "Corpo Teste");
String destinatario = email.getDestinatario(); // "test@example.com"
email.setAssunto("Novo Assunto"); // Atualiza o assunto do e-mail
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe EmailDTO é encapsular os dados de uma mensagem de e-mail. Ela fornece uma maneira conveniente de criar e manipular objetos de e-mail, oferecendo métodos getter e setter para os campos destinatario, assunto e corpo.

#### Métodos

A classe EmailDTO não possui métodos adicionais além dos métodos getter e setter padrão gerados pela biblioteca Lombok.

#### Campos

- `destinatario`: Representa o destinatário do e-mail.
- `assunto`: Representa o assunto do e-mail.
- `corpo`: Representa o corpo do e-mail.

## EmpresaDTO.class

A classe `EmpresaDTO` é um objeto de transferência de dados que representa uma empresa. Ela contém campos para o ID da empresa, CNPJ (Cadastro Nacional da Pessoa Jurídica), nome, saldo, taxa de depósito e taxa de saque. Também inclui métodos getter e setter para esses campos, bem como construtores para criar instâncias da classe.

### Exemplo de Uso

```java
EmpresaDTO empresaDTO = new EmpresaDTO();
empresaDTO.setCnpj("84867778000192");
empresaDTO.setNome("Teste Empresa");
empresaDTO.setSaldo(1000.0);
empresaDTO.setTaxaDeposito(0.1);
empresaDTO.setTaxaSaque(0.2);
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe EmpresaDTO é fornecer uma representação estruturada dos dados de uma empresa. Isso permite fácil acesso e manipulação das informações da empresa por meio de seus métodos getter e setter.

#### Métodos

A classe EmpresaDTO não possui métodos adicionais além dos métodos getter e setter padrão gerados pelas anotações do Lombok.

#### Campos

- `id`: Representa o ID da empresa. É do tipo Long.
- `cnpj`: Representa o CNPJ (Cadastro Nacional da Pessoa Jurídica) da empresa. É do tipo String.
- `nome`: Representa o nome da empresa. É do tipo String.
- `saldo`: Representa o saldo da empresa. É do tipo Double.
- `taxaDeposito`: Representa a taxa de depósito da empresa. É do tipo Double.
- `taxaSaque`: Representa a taxa de saque da empresa. É do tipo Double.

## ErroDTO.class

A classe `ErroDTO` é uma classe Java que representa uma resposta de erro. Ela contém dois campos: `status` do tipo `HttpStatus` e `mensagem` do tipo `String`. A classe é anotada com anotações do Lombok para gerar métodos getter e setter, bem como um construtor com todos os campos.

### Exemplo de Uso

```java
ErroDTO erro = new ErroDTO(HttpStatus.BAD_REQUEST, "Entrada inválida");
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe ErroDTO é encapsular informações sobre uma resposta de erro. Ela fornece uma maneira conveniente de armazenar e recuperar o código de status HTTP e a mensagem de erro.

#### Métodos

A classe ErroDTO não possui métodos adicionais. Ela depende dos métodos getter e setter gerados pelas anotações do Lombok.

#### Campos

- `status`: Representa o código de status HTTP da resposta de erro. É do tipo HttpStatus, que é uma enumeração fornecida pelo Spring Framework.
- `mensagem`: Representa a mensagem de erro associada à resposta de erro. É do tipo String.

## SaldoInsuficienteDTO.class

A classe `SaldoInsuficienteDTO` é um objeto de transferência de dados (DTO) que representa informações sobre saldo insuficiente em uma conta. Ela contém campos para a entidade, nome, saldo atual, operação e valor da transação.

### Exemplo de Uso

```java
SaldoInsuficienteDTO saldoDTO = new SaldoInsuficienteDTO("Entidade", "John Doe", 100, "Saque", 150);
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe SaldoInsuficienteDTO é armazenar e transferir dados relacionados a saldo insuficiente em uma conta. Ela serve como um contêiner para as informações relevantes, permitindo que sejam facilmente passadas entre diferentes partes de um programa.

#### Métodos

A classe SaldoInsuficienteDTO não possui métodos adicionais além dos getters e setters padrão gerados pelas anotações do Lombok.

#### Campos

- `entidade`: Representa a entidade associada ao saldo insuficiente.
- `nome`: Representa o nome do titular da conta.
- `saldo`: Representa o saldo atual da conta.
- `operacao`: Representa o tipo de operação que resultou em saldo insuficiente.
- `valorTransacao`: Representa o valor da transação que causou o saldo insuficiente.

## TaxaDTO.class

A classe `TaxaDTO` é uma classe Java que representa um objeto de transferência de dados para uma taxa. Ela contém campos para o ID da taxa, tipo e valor.

### Exemplo de Uso

```java
TaxaDTO taxaDTO = new TaxaDTO();
taxaDTO.setId(1L);
taxaDTO.setTipoTaxa("DEPÓSITO");
taxaDTO.setValor(0.5);
```

### Análise de Código

#### Principais Funcionalidades

A principal funcionalidade da classe TaxaDTO é fornecer uma maneira estruturada de transferir dados de taxa entre diferentes camadas de uma aplicação. Ela encapsula as informações da taxa e permite uma manipulação e transporte fáceis dos dados.

#### Métodos

A classe TaxaDTO não possui métodos personalizados. Ela apenas fornece métodos getter e setter para os campos de ID da taxa, tipo e valor.

#### Campos

- `id`: Representa o ID da taxa. É do tipo Long.
- `tipoTaxa`: Representa o tipo da taxa. É do tipo String.
- `valor`: Representa o valor da taxa. É do tipo Double.

## TransacaoDTO.class

A classe `TransacaoDTO` é um objeto de transferência de dados que representa uma transação. Ela contém campos para o ID da transação, tipo, valor, data da transação, nome do cliente e nome da empresa. Também inclui métodos getter e setter para esses campos, bem como construtores para criar instâncias da classe.

### Exemplo de Uso

```java
TransacaoDTO transacao = new TransacaoDTO(1L, "Depósito", 100.0, "2021-01-01", "Cliente 1", "Empresa 1");
Long id = transacao.getId();
String tipo = transacao.getTipo();
double valor = transacao.getValor();
String dataTransacao = transacao.getDataTransacao();
String clienteNome = transacao.getClienteNome();
String empresaNome = transacao.getEmpresaNome();
```

### Análise de Código

#### Principais Funcionalidades

- Representa uma transação com vários atributos, como ID, tipo, valor, data, nome do cliente e nome da empresa.
- Fornece métodos getter e setter para acessar e modificar os atributos da transação.
- Inclui construtores para criar instâncias da classe TransacaoDTO com diferentes combinações de valores de atributos.

#### Métodos

-

#### Campos

- `id`: Representa o ID da transação.
- `tipo`: Representa o tipo da transação.
- `valor`: Representa o valor da transação.
- `dataTransacao`: Representa a data da transação.
- `clienteNome`: Representa o nome do cliente associado à transação.
- `empresaNome`: Representa o nome da empresa associada à transação.