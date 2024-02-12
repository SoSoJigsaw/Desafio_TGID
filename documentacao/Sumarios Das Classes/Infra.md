# Infra

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