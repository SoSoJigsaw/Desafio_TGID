# Util

## AppConfig.java

A classe `AppConfig` é uma classe de configuração em uma aplicação Spring. Ela é responsável por definir e configurar beans, especificamente o bean `RestTemplate`.

### Exemplo de Uso

```java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```

### Análise de Código

#### Principais funcionalidades

- A principal funcionalidade da classe AppConfig é definir e configurar beans para a aplicação Spring.

#### Métodos

- `restTemplate()`: Este método é anotado com @Bean e é responsável por criar e retornar uma nova instância da classe RestTemplate. O RestTemplate é uma classe fornecida pelo framework Spring para fazer requisições HTTP.

## CalcularTaxaImpl.java

A classe `CalcularTaxaImpl` é uma implementação da interface `CalcularTaxa`. Ela fornece métodos para calcular as taxas de depósito e saque para uma transação.

### Exemplo de Uso

```java
CalcularTaxaImpl calcularTaxa = new CalcularTaxaImpl();
double taxaDeposito = calcularTaxa.calcularTaxaDeposito(1000, 0.05);
double taxaSaque = calcularTaxa.calcularTaxaSaque(500, 0.1);
```

### Análise de Código

#### Principais funcionalidades

- A principal funcionalidade da classe `CalcularTaxaImpl` é calcular as taxas para transações de depósito e saque. Ela implementa a interface CalcularTaxa, que define o contrato para esses cálculos.

#### Métodos

- `calcularTaxaDeposito(double valor, double taxa)`: Este método calcula a taxa de depósito para uma transação específica. Ele recebe o valor da transação (`valor`) e a taxa (`taxa`) como parâmetros e retorna o valor total da taxa.
- `calcularTaxaSaque(double valor, double taxa)`: Este método calcula a taxa de saque para uma transação específica. Ele recebe o valor da transação (`valor`) e a taxa (`taxa`) como parâmetros e retorna o valor total da taxa.