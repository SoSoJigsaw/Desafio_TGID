# Util

## <p align="center"> Índices </p>

<div align="center">

|                Nesse Documento                 |                                                    Sumários de Classes                                                     |
|:----------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
|        [AppConfig.java](#appconfigjava)        |  [Controllers](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Controllers.md)  |
| [CalcularTaxaImpl.java](#calculartaxaimpljava) |         [DTOs](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md)          |
|                                                |     [Entities](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Entities.md)     |
|                                                |   [Exceptions](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md)   |
|                                                |        [Infra](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md)        |
|                                                |        [Kafka](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md)        |
|                                                | [Notification](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md) |
|                                                |     [Services](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md)     |
|                                                |         [Util](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Util.md)         |
|                                                |   [Validation](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md)   |

</div>

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

<br>
<br>



<p align="left"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md"><<< Anterior: Services</a></p>
<p align="right"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md">Próximo: Validation >>></a></p>

