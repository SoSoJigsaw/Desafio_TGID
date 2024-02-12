# Validation

## CNPJValidator.class

A classe `CNPJValidator` é responsável por validar o CNPJ (Cadastro Nacional da Pessoa Jurídica brasileiro) usando um algoritmo específico. Ela implementa a interface `ConstraintValidator` e é anotada com `@Component` para ser reconhecida como um bean do Spring.

### Exemplo de Uso

```java
CNPJValidator validator = new CNPJValidator();
boolean isValid = validator.isValid("12.345.678/0001-90", null);
System.out.println(isValid); // Saída: true
```

### Análise de Código

#### Principais funcionalidades

- A principal funcionalidade da classe `CNPJValidator` é validar números de CNPJ aplicando um algoritmo específico. Ela verifica se o CNPJ tem 14 dígitos, calcula os dígitos de verificação usando um algoritmo de multiplicação e verifica se os dígitos calculados correspondem ao CNPJ fornecido.

#### Métodos

- `initialize(CNPJ constraintAnnotation)`: Este método é chamado durante a inicialização do validador e pode ser usado para realizar qualquer configuração ou inicialização necessária.
- `isValid(String cnpj, ConstraintValidatorContext context)`: Este método é responsável por validar o CNPJ. Ele remove qualquer caractere não numérico do CNPJ, verifica se tem 14 dígitos, calcula os dígitos de verificação usando um algoritmo de multiplicação e os compara com o CNPJ fornecido. Se ocorrer qualquer exceção durante o processo de validação, ele registra um erro e lança uma `CnpjInvalidoException`.

## CPFValidator.class

A classe `CPFValidator` é responsável por validar números de CPF (Cadastro de Pessoas Físicas brasileiro). Ela implementa a interface `ConstraintValidator` e define a lógica para validar uma string de CPF.

### Exemplo de Uso

```java
CPFValidator cpfValidator = new CPFValidator();
boolean isValid = cpfValidator.isValid("12345678901", null);
System.out.println(isValid); // Saída: true
```

### Análise de Código

#### Principais funcionalidades

- Remover caracteres não numéricos da string de CPF.
- Verificar se o CPF tem 11 dígitos.
- Aplicar o algoritmo de validação de CPF para verificar os dígitos de verificação.
- Lidar com exceções e lançar uma exceção personalizada `CpfInvalidoException` se a validação falhar.

#### Métodos

- `initialize(CPF constraintAnnotation)`: Este método é chamado durante a inicialização do validador e pode ser usado para realizar qualquer configuração ou tarefa de inicialização. Neste caso, ele não tem implementação.
- `isValid(String cpf, ConstraintValidatorContext context)`: Este método é responsável por validar a string de CPF. Ele remove caracteres não numéricos do CPF, verifica se tem 11 dígitos, aplica o algoritmo de validação de CPF para calcular os dígitos de verificação e verifica se eles correspondem ao CPF fornecido. Se ocorrer qualquer exceção durante o processo de validação, ele registra um erro e lança uma `CpfInvalidoException`.

## TaxaValidator.class

A classe `TaxaValidator` é uma implementação personalizada de validador para a anotação `Taxa`. Ela verifica se o valor do campo anotado é "DEPÓSITO" ou "SAQUE".

### Exemplo de Uso

```java
@Taxa
private String tipoTransacao;
```

### Análise de Código

#### Principais funcionalidades

- A principal funcionalidade da classe `TaxaValidator` é validar se o valor do campo anotado é "DEPÓSITO" ou "SAQUE".

#### Métodos

- `initialize(Taxa constraintAnnotation)`: Este método é chamado durante a inicialização do validador e pode ser usado para realizar qualquer configuração ou tarefa de inicialização necessária. Neste caso, ele não tem implementação.
- `isValid(String taxa, ConstraintValidatorContext context)`: Este método é responsável por realizar a lógica de validação. Ele verifica se o valor do campo anotado (`taxa`) é igual a "DEPÓSITO" ou "SAQUE" usando o método `Objects.equals()`. Retorna `true` se o valor for válido e `false` caso contrário.