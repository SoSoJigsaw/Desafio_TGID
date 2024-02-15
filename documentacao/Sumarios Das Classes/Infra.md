# Infra

## <p align="center"> Índices </p>

<div align="center">

|                    Nesse Documento                     |                                                    Sumários de Classes                                                     |
|:------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
| [RestExceptionHandler.java](#restexceptionhandlerjava) |  [Controllers](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Controllers.md)  |
|                                                        |         [DTOs](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md)          |
|                                                        |     [Entities](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Entities.md)     |
|                                                        |   [Exceptions](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md)   |
|                                                        |        [Infra](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md)        |
|                                                        |        [Kafka](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md)        |
|                                                        | [Notification](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md) |
|                                                        |     [Services](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md)     |
|                                                        |         [Util](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Util.md)         |
|                                                        |   [Validation](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md)   |

</div>

## RestExceptionHandler.java

A classe `RestExceptionHandler` é uma classe de conselho de controlador que lida com exceções lançadas pelos pontos finais da API REST. Ele fornece tratamento centralizado de exceções e retorna respostas de erro apropriadas para o cliente.

### Exemplo de Uso

```java
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    private ResponseEntity<?> ClienteNaoEncontradoExceptionHandler(ClienteNaoEncontradoException e) {
        // Manipula a exceção e retorna uma resposta de erro
    }

    // Outros métodos de manipulador de exceção

}
```

### Análise de Código

#### Principais funcionalidades

- Lida com várias exceções lançadas pelos pontos finais da API REST.
- Retorna respostas de erro apropriadas com códigos de status HTTP e mensagens de erro.

<br>
<br>



<p align="left"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md"><<< Anterior: Exceptions</a></p>
<p align="right"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md">Próximo: Kafka >>></a></p>

