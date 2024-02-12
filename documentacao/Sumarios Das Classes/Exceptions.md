# Exceptions

## <p align="center"> Índices </p>

<div align="center">

|                      Nesse Documento                     |                                                    Sumários de Classes                                                     |
|:--------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
|                                                          |  [Controllers](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Controllers.md)  |
|                                                          |         [DTOs](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md)          |
|                                                          |     [Entities](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Entities.md)     |
|                                                          |   [Exceptions](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md)   |
|                                                          |        [Infra](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md)        |
|                                                          |        [Kafka](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md)        |
|                                                          | [Notification](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md) |
|                                                          | [Repositories](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Repositories.md) |
|                                                          |     [Services](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md)     |
|                                                          |         [Util](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Util.md)         |
|                                                          |   [Validation](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md)   |

</div>

## ClienteNaoEncontradoException.java

A classe `ClienteNaoEncontradoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações onde uma lista de clientes não pode ser encontrada ou quando um cliente específico não é encontrado.

### Exemplo de Uso

```java
// Criando uma nova instância de ClienteNaoEncontradoException com uma mensagem personalizada
throw new ClienteNaoEncontradoException("Cliente não encontrado");

// Criando uma nova instância de ClienteNaoEncontradoException com uma exceção como causa
throw new ClienteNaoEncontradoException(new RuntimeException("Mensagem de erro"));
```

### Análise do Código

#### Principais Funcionalidades

- Representa uma exceção que ocorre quando uma lista de clientes não pode ser encontrada ou quando um cliente específico não é encontrado.
- Fornece construtores para criar instâncias da exceção com uma mensagem personalizada ou com uma exceção como causa.

## ClienteRegistroException.java

A classe `ClienteRegistroException` é uma classe de exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas ao registro de clientes.

### Exemplo de Uso

```java
// Criando uma nova instância de ClienteRegistroException com um objeto Exception
ClienteRegistroException exception1 = new ClienteRegistroException(new Exception("Mensagem de erro"));

// Criando uma nova instância de ClienteRegistroException com uma mensagem de erro personalizada
ClienteRegistroException exception2 = new ClienteRegistroException("Mensagem de erro personalizada");

// Criando uma nova instância de ClienteRegistroException com uma mensagem de erro personalizada e um objeto NullPointerException
ClienteRegistroException exception3 = new ClienteRegistroException("Mensagem de erro personalizada", new NullPointerException("Exceção de ponteiro nulo"));
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `ClienteRegistroException` é fornecer uma exceção personalizada para lidar com erros relacionados ao registro de clientes. Permite a encapsulação de mensagens de erro e exceções relacionadas ao registro de clientes.

#### Métodos

- `ClienteRegistroException(Exception e)`: Construtor que recebe um objeto Exception como parâmetro e define a mensagem de erro da exceção para "O cliente não pôde ser registrado: " seguido pela mensagem da exceção fornecida.
- `ClienteRegistroException(String mensagem)`: Construtor que recebe uma mensagem de erro personalizada como parâmetro e define a mensagem de erro da exceção para a mensagem fornecida.
- `ClienteRegistroException(String mensagem, NullPointerException e)`: Construtor que recebe uma mensagem de erro personalizada e um objeto NullPointerException como parâmetros. Define a mensagem de erro da exceção para "O cliente não pôde ser registrado: " seguido pela mensagem fornecida.

## ClienteRemocaoException.java

A classe `ClienteRemocaoException` é uma classe de exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas à remoção de um cliente.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma exceção
} catch (ClienteRemocaoException e) {
    // lidar com a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `ClienteRemocaoException` é fornecer uma exceção personalizada para casos em que um cliente não pode ser deletado. Permite a inclusão de uma mensagem de erro e a exceção original que causou a falha na deleção.

#### Métodos

- `ClienteRemocaoException(Exception e)`: Construtor que recebe um objeto Exception como parâmetro e define a mensagem de exceção para "O cliente não pôde ser deletado: " seguido pela mensagem da exceção original.
- `ClienteRemocaoException(String mensagem)`: Construtor que recebe uma mensagem String como parâmetro e define a mensagem de exceção para a mensagem fornecida.

## CnpjInvalidoException.java

A classe `CnpjInvalidoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas a valores inválidos de CNPJ (Cadastro Nacional da Pessoa Jurídica).

### Exemplo de Uso

```java
try {
    // código que valida um CNPJ
    if (!isValidCnpj(cnpj)) {
        throw new CnpjInvalidoException();
    }
} catch (CnpjInvalidoException e) {
    System.out.println(e.getMessage());
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `CnpjInvalidoException` é fornecer uma maneira de lidar com exceções relacionadas a valores inválidos de CNPJ. Permite que o programa lance e capture essa exceção específica quando necessário.

#### Métodos

- `CnpjInvalidoException()`: Este é o construtor padrão da classe. Define a mensagem de exceção como "CNPJ Inválido".
- `CnpjInvalidoException(Exception e)`: Este construtor recebe um objeto Exception como parâmetro e define a mensagem de exceção para "A validação do CNPJ não pôde ser realizada: " seguido pela mensagem da exceção fornecida.

## CpfInvalidoException.java

A classe `CpfInvalidoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas a entradas inválidas de CPF (Cadastro de Pessoas Físicas).

### Exemplo de Uso

```java
try {
    // código que valida um CPF
} catch (CpfInvalidoException e) {
    // lidar com a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `CpfInvalidoException` é fornecer uma maneira de lidar com exceções relacionadas a entradas inválidas de CPF. Permite que o programa capture e manipule essas exceções separadamente de outros tipos de exceções.

#### Métodos

- `CpfInvalidoException()`: Este é o construtor padrão da classe. Cria uma instância da exceção com uma mensagem de erro padrão "CPF Inválido".
- `CpfInvalidoException(Exception e)`: Este construtor cria uma instância da exceção com uma mensagem de erro personalizada que inclui a mensagem da exceção fornecida.

## EmpresaNaoEncontradaException.java

A classe `EmpresaNaoEncontradaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com o caso em que uma lista de empresas não pode ser encontrada.

### Exemplo de Uso

```java
try {
    throw new EmpresaNaoEncontradaException("A lista de Empresas não pôde ser encontrada");
} catch (EmpresaNaoEncontradaException e) {
    System.out.println(e.getMessage());
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `EmpresaNaoEncontradaException` é fornecer uma exceção personalizada para quando uma lista de empresas não pode ser encontrada.

#### Métodos

- `EmpresaNaoEncontradaException(Exception e)`: Construtor que recebe um objeto Exception como parâmetro e define a mensagem da exceção para "A lista de Empresas não pôde ser encontrada: " seguida pela mensagem da exceção fornecida.
- `EmpresaNaoEncontradaException(String mensagem)`: Construtor que recebe uma mensagem de String como parâmetro e define a mensagem da exceção para a mensagem fornecida.

## EmpresaRegistroException.java

A classe `EmpresaRegistroException` é uma classe de exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas ao registro de uma empresa.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma exceção
} catch (Exception e) {
    throw new EmpresaRegistroException(e);
}
```

### Análise do Código

#### Principais Funcionalidades

- Representa uma exceção que ocorre quando o registro de uma empresa falha.
- Fornece construtores para criar uma instância da exceção com diferentes mensagens de erro e causas.

#### Métodos

- `EmpresaRegistroException(Exception e)`: Constrói uma nova exceção com a mensagem "A empresa não pôde ser registrada: " seguida pela mensagem da exceção fornecida.
- `EmpresaRegistroException(String mensagem)`: Constrói uma nova exceção com a mensagem de erro fornecida.
- `EmpresaRegistroException(String mensagem, NullPointerException e)`: Constrói uma nova exceção com a mensagem "A empresa não pôde ser registrada: " seguida pela mensagem de erro fornecida.

## EmpresaRemocaoException.java

A classe `EmpresaRemocaoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas à exclusão de uma empresa.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma exceção
    throw new EmpresaRemocaoException("Mensagem de erro");
} catch (EmpresaRemocaoException e) {
    // lidar com a exceção
    System.out.println(e.getMessage());
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `EmpresaRemocaoException` é fornecer uma exceção personalizada para casos em que uma empresa não pode ser excluída. Isso permite a encapsulação da mensagem de erro e da exceção original.

#### Métodos

- `EmpresaRemocaoException(Exception e)`: Construtor que recebe um parâmetro Exception e define a mensagem de erro para "A empresa não pôde ser deletada: " seguido pela mensagem da exceção fornecida.
- `EmpresaRemocaoException(String mensagem)`: Construtor que recebe um parâmetro String e define a mensagem de erro para a mensagem fornecida.

## NotificacaoClienteException.java

A classe `NotificacaoClienteException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções que ocorrem durante o processo de envio de uma notificação por e-mail para um cliente.

### Exemplo de Uso

```java
try {
    // código que envia um e-mail para um cliente
} catch (Exception e) {
    throw new NotificacaoClienteException(e);
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `NotificacaoClienteException` é fornecer uma exceção personalizada para lidar com erros que ocorrem durante o processo de envio de uma notificação por e-mail para um cliente. Ela encapsula a mensagem da exceção original e fornece uma mensagem de erro mais específica.

#### Métodos

- A classe `NotificacaoClienteException` não possui métodos adicionais além do construtor herdado da classe `RuntimeException`.

## NotificacaoEmpresaException.class

A classe `NotificacaoEmpresaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas ao envio de notificações para uma empresa.

### Exemplo de Uso

```java
try {
    // código que envia notificação para uma empresa
} catch (Exception e) {
    throw new NotificacaoEmpresaException(e);
}
```

### Análise do Código

#### Principais Funcionalidades

- Representa uma exceção que ocorre quando há uma falha no envio de uma notificação para uma empresa.
- Fornece construtores para criar uma instância da exceção com uma mensagem de erro específica.

#### Métodos

- `NotificacaoEmpresaException(Exception e)`: Construtor que recebe um objeto Exception como parâmetro e define a mensagem de erro para "Falha no envio de notificação para a empresa: " seguido da mensagem da exceção.
- `NotificacaoEmpresaException(HttpStatusCode status)`: Construtor que recebe um objeto HttpStatusCode como parâmetro e define a mensagem de erro para "Erro ao enviar callback. Status code: " seguido do código de status.

## SaldoInsuficienteException.class

A classe `SaldoInsuficienteException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações em que há saldo insuficiente em uma conta. A classe converte o objeto `SaldoInsuficienteDTO` em uma string JSON e a armazena como mensagem da exceção.

### Exemplo de Uso

```java
SaldoInsuficienteDTO saldoInsuficienteDTO = new SaldoInsuficienteDTO("Entidade", "Nome", 100, "Operacao", 50);
throw new SaldoInsuficienteException(saldoInsuficienteDTO);
```

### Análise do Código

#### Principais Funcionalidades

- Representa uma exceção personalizada para lidar com situações de saldo insuficiente.
- Converte o objeto SaldoInsuficienteDTO em uma string JSON e define como mensagem da exceção.

#### Métodos

- `SaldoInsuficienteException(SaldoInsuficienteDTO saldoInsuficiente)`: Construtor que recebe um objeto SaldoInsuficienteDTO como parâmetro e o converte em uma string JSON para definir como mensagem da exceção.
- `getMessage()`: Sobrescreve o método getMessage() da classe RuntimeException para retornar a mensagem da exceção.

#### Campos

- `saldoInsuficiente`: Um campo privado e final que armazena a representação em string JSON do objeto SaldoInsuficienteDTO.

## SaldoNegativoException.class

A classe `SaldoNegativoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações em que um saldo negativo é encontrado em um sistema financeiro.

### Exemplo de Uso

```java
try {
    // código que pode resultar em um saldo negativo
    throw new SaldoNegativoException("Saldo negativo detectado");
} catch (SaldoNegativoException e) {
    // manipular a exceção
    System.out.println(e.getMessage());
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `SaldoNegativoException` é fornecer uma maneira de lidar e propagar exceções relacionadas a saldos negativos em um sistema financeiro.

#### Métodos

- A classe `SaldoNegativoException` herda todos os métodos da classe RuntimeException. Não define nenhum método adicional.

## TaxaInvalidoException.class

A classe `TaxaInvalidoException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com tipos de taxa de transação inválidos.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma TaxaInvalidoException
} catch (TaxaInvalidoException e) {
    // manipular a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TaxaInvalidoException` é lidar com exceções relacionadas a tipos de taxa de transação inválidos.

#### Métodos

- `TaxaInvalidoException()`: Este é um método construtor que cria uma nova instância da classe TaxaInvalidoException. Ele define a mensagem de exceção como "Tipo de taxa de transação inválida. Valores esperados: DEPÓSITO ou SAQUE".
- `TaxaInvalidoException(Exception e)`: Este é outro método construtor que cria uma nova instância da classe TaxaInvalidoException. Ele recebe uma exceção e como parâmetro e define a mensagem de exceção como "A atualização da taxa falhou: " seguido da mensagem da exceção fornecida.

## TaxaNegativaException.class

A classe `TaxaNegativaException` é uma classe de exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações em que um valor de taxa negativo é encontrado.

### Exemplo de Uso

```java
try {
    // Algum código que pode lançar uma TaxaNegativaException
} catch (TaxaNegativaException e) {
    System.out.println(e.getMessage());
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TaxaNegativaException` é fornecer uma maneira de lidar e propagar exceções relacionadas a valores de taxa negativos.

#### Métodos

- A classe `TaxaNegativaException` não define nenhum método adicional. Ela herda todos os métodos da classe `RuntimeException`.

## TaxaNulaException.class

A classe `TaxaNulaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações em que um valor de taxa é nulo.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma TaxaNulaException
} catch (TaxaNulaException e) {
    // manipular a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TaxaNulaException` é fornecer uma maneira de lidar com situações em que um valor de taxa é nulo. Isso permite que o código lance uma exceção quando essa situação ocorre e fornece um mecanismo para capturar e lidar com a exceção.

#### Métodos

- `TaxaNulaException(String mensagem)`: Este é o construtor da classe `TaxaNulaException`. Ele recebe uma mensagem como parâmetro e chama o construtor da superclasse RuntimeException com a mensagem fornecida.

## TransacaoInvalidaException.class

A classe `TransacaoInvalidaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com erros que ocorrem durante o processamento de transações.

### Exemplo de Uso

```java
try {
    // código que pode lançar TransacaoInvalidaException
} catch (TransacaoInvalidaException e) {
    // manipular a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TransacaoInvalidaException` é fornecer uma maneira de lidar e propagar exceções que ocorrem durante o processamento de transações.

#### Métodos

- `TransacaoInvalidaException(String tipo, Exception e)`: Este construtor cria uma nova instância de TransacaoInvalidaException com uma mensagem que inclui o tipo de transação e a mensagem da exceção subjacente.
- `TransacaoInvalidaException(String tipo, String mensagem, Exception e)`: Este construtor cria uma nova instância de TransacaoInvalidaException com uma mensagem personalizada que inclui o tipo de transação e uma mensagem de erro específica, juntamente com a mensagem da exceção subjacente.

## TransacaoNaoEncontradaException.class

A classe `TransacaoNaoEncontradaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com situações em que uma lista de transações não pode ser encontrada.

### Exemplo de Uso

```java
try {
    // código que pode lançar TransacaoNaoEncontradaException
} catch (TransacaoNaoEncontradaException e) {
    // manipular a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- Representa uma exceção que ocorre quando uma lista de transações não pode ser encontrada.
- Fornece construtores para criar uma instância da exceção com uma mensagem de erro personalizada ou com uma exceção subjacente.

#### Métodos

- `TransacaoNaoEncontradaException(Exception e)`: Construtor que cria uma instância da exceção com uma mensagem de erro personalizada com base na exceção subjacente fornecida.
- `TransacaoNaoEncontradaException(String mensagem)`: Construtor que cria uma instância da exceção com a mensagem de erro especificada.

## TransacaoNegativaException.class

A classe `TransacaoNegativaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com casos em que o valor de uma transação é negativo.

### Exemplo de Uso

```java
TransacaoNegativaException exception = new TransacaoNegativaException("depósito");
throw exception;
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TransacaoNegativaException` é fornecer uma exceção personalizada para casos em que o valor de uma transação é negativo.

#### Métodos

- A classe `TransacaoNegativaException` não possui métodos adicionais além do construtor padrão herdado da classe `RuntimeException`.

## TransacaoRemocaoException.class

A classe `TransacaoRemocaoException` é uma classe de exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com exceções relacionadas à exclusão de registros de transação.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma TransacaoRemocaoException
} catch (TransacaoRemocaoException e) {
    // manipular a exceção
}
```

### Análise do Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TransacaoRemocaoException` é fornecer uma exceção personalizada para casos em que um registro de transação não pode ser deletado.

#### Métodos

- `TransacaoRemocaoException(Exception e)`: Este construtor recebe um objeto Exception como parâmetro e define a mensagem da exceção para "O registro de transação não pôde ser deletado: " seguido pela mensagem da exceção fornecida.
- `TransacaoRemocaoException(String mensagem)`: Este construtor recebe uma mensagem de tipo String como parâmetro e define a mensagem da exceção para a mensagem fornecida.

## TransacaoZeradaException.class

A classe `TransacaoZeradaException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para representar uma exceção que ocorre quando o valor de uma transação é zero.

### Exemplo de Uso

```java
TransacaoZeradaException exception = new TransacaoZeradaException("depósito");
throw exception;
```

### Análise de Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `TransacaoZeradaException` é fornecer uma maneira de lidar e representar uma exceção quando o valor de uma transação é zero.

#### Métodos

- A classe `TransacaoZeradaException` não possui métodos adicionais além do construtor herdado da classe `RuntimeException`.

## ViolacaoConstraintCnpjException.java

A classe `ViolacaoConstraintCnpjException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com violações de uma restrição relacionada ao CNPJ (Cadastro Nacional da Pessoa Jurídica) na aplicação.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma ViolacaoConstraintCnpjException
} catch (ViolacaoConstraintCnpjException e) {
    // lidar com a exceção
}
```

### Análise de Código

#### Principais Funcionalidades

- A principal funcionalidade da classe `ViolacaoConstraintCnpjException` é fornecer um tipo específico de exceção para violações da restrição do CNPJ. Isso permite um tratamento de erro mais específico e pode ser capturado separadamente de outras exceções.

#### Métodos

- A classe `ViolacaoConstraintCnpjException` não define nenhum método adicional. Ela herda os métodos da classe `RuntimeException`.

## ViolacaoConstraintCpfException.class

A classe `ViolacaoConstraintCpfException` é uma exceção personalizada que estende a classe `RuntimeException`. É usada para lidar com violações de uma restrição relacionada ao CPF (Cadastro de Pessoa Física) na aplicação.

### Exemplo de Uso

```java
try {
    // código que pode lançar uma ViolacaoConstraintCpfException
} catch (ViolacaoConstraintCpfException e) {
    // manipular a exceção
}
```

### Análise de Código

#### Principais funcionalidades

- A principal funcionalidade da classe `ViolacaoConstraintCpfException` é fornecer um tipo específico de exceção para violações da restrição do CPF. Isso permite que a aplicação lide com essas violações separadamente de outras exceções.

#### Métodos

- A classe `ViolacaoConstraintCpfException` não define nenhum método adicional além do construtor herdado da classe `RuntimeException`.