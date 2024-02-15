# Kafka

## <p align="center"> Índices </p>

<div align="center">

|                   Nesse Documento                    |                                                    Sumários de Classes                                                     |
|:----------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
| [KafkaProducerConfig.java](#kafkaproducerconfigjava) |  [Controllers](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Controllers.md)  |
|   [KafkaProducerImpl.java](#kafkaproducerimpljava)   |         [DTOs](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/DTO.md)          |
| [KafkaConsumerConfig.java](#kafkaconsumerconfigjava) |     [Entities](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Entities.md)     |
|   [KafkaConsumerImpl.java](#kafkaconsumerimpljava)   |   [Exceptions](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Exceptions.md)   |
|                                                      |        [Infra](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md)        |
|                                                      |        [Kafka](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Kafka.md)        |
|                                                      | [Notification](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md) |
|                                                      |     [Services](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Services.md)     |
|                                                      |         [Util](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Util.md)         |
|                                                      |   [Validation](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Validation.md)   |

</div>

## KafkaProducerConfig.java

A classe `KafkaProducerConfig` é uma classe de configuração para configurar um produtor Kafka em uma aplicação Spring Boot.

### Exemplo de Uso

```java
KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
kafkaProducerConfig.producerFactory();
kafkaProducerConfig.kafkaTemplate();
kafkaProducerConfig.transacaoRequestTopicBuilder();
kafkaProducerConfig.callbackRequestTopicBuilder();
```

### Análise de Código

#### Principais funcionalidades

- Configura a fábrica do produtor Kafka com as propriedades necessárias.
- Cria um modelo Kafka para enviar mensagens para tópicos Kafka.
- Constrói tópicos Kafka com nomes, partições e réplicas especificadas.

#### Métodos

- `producerFactory()`: Cria um objeto ProducerFactory com as propriedades de configuração necessárias para o produtor Kafka.
- `kafkaTemplate()`: Cria um objeto KafkaTemplate usando o bean producerFactory.
- `transacaoRequestTopicBuilder()`: Constrói um tópico Kafka para solicitações de transação com o nome, partições e réplicas especificadas.
- `callbackRequestTopicBuilder()`: Constrói um tópico Kafka para solicitações de retorno com o nome, partições e réplicas especificadas.
- `setValueSerializerClassConfig(Object o)`: Método setter para configurar a classe serializadora de valores.
- `setKeySerializerClassConfig(Object o)`: Método setter para configurar a classe serializadora de chaves.
- `setBootstrapServersConfig(Object o)`: Método setter para configurar os servidores de inicialização.

#### Campos

- `transacaoResquestTopic`: Armazena o nome do tópico de solicitação de transação.
- `callbackRequestTopic`: Armazena o nome do tópico de solicitação de retorno.

## KafkaProducerImpl.java

A classe `KafkaProducerImpl` é responsável por enviar mensagens para tópicos Kafka. Ela utiliza o `KafkaTemplate` e o `ObjectMapper` para converter os objetos de mensagem em strings JSON e enviá-los para os tópicos apropriados.

### Exemplo de Uso

```java
KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>();
ObjectMapper objectMapper = new ObjectMapper();
KafkaProducerImpl kafkaProducer = new KafkaProducerImpl(kafkaTemplate, objectMapper);

EmailDTO email = new EmailDTO("test@example.com", "Assunto Teste", "Corpo Teste");
kafkaProducer.enviarMensagemTransacaoCliente(email);

CallbackDTO callback = new CallbackDTO("https://example.com/callback", "Mensagem Teste");
kafkaProducer.enviarMensagemTransacaoEmpresa(callback);
```

### Análise de Código

#### Principais funcionalidades

- Converte objetos `EmailDTO` e `CallbackDTO` em strings JSON usando o `ObjectMapper`.
- Envia as strings JSON para tópicos Kafka usando o `KafkaTemplate`.
- Registra o envio bem-sucedido de mensagens.

#### Métodos

- `enviarMensagemTransacaoCliente(EmailDTO email)`: Converte o objeto EmailDTO em uma string JSON e envia-o para o tópico Kafka especificado por transacaoResquestTopic.
- `enviarMensagemTransacaoEmpresa(CallbackDTO callback)`: Converte o objeto CallbackDTO em uma string JSON e envia-o para o tópico Kafka especificado por callbackRequestTopic.

#### Campos

- `transacaoResquestTopic`: O nome do tópico Kafka para solicitações de transação.
- `callbackRequestTopic`: O nome do tópico Kafka para solicitações de retorno.
- `kafkaTemplate`: O KafkaTemplate usado para enviar mensagens para Kafka.
- `objectMapper`: O ObjectMapper usado para converter objetos em strings JSON.

## KafkaConsumerConfig.java

Este código é uma classe de configuração para um consumidor Kafka em uma aplicação Spring Boot. Ele define as propriedades e beans necessários para criar uma fábrica de consumidores Kafka e uma fábrica de contêineres de ouvintes Kafka.

### Exemplo de Uso

```java
KafkaConsumerConfig config = new KafkaConsumerConfig();
ConsumerFactory<String, String> consumerFactory = config.consumerFactory();
ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory = config.kafkaListenerContainerFactory();
```

### Análise de Código

#### Principais funcionalidades

- Cria uma fábrica de consumidores Kafka com as propriedades necessárias para se conectar a um corretor Kafka.
- Cria uma fábrica de contêineres de ouvintes Kafka que usa a fábrica de consumidores para criar contêineres de ouvintes Kafka.

#### Métodos

- `consumerFactory()`: Cria um bean ConsumerFactory com as propriedades necessárias para se conectar a um corretor Kafka.
- `kafkaListenerContainerFactory()`: Cria um bean ConcurrentKafkaListenerContainerFactory que usa o bean consumerFactory para criar contêineres de ouvintes Kafka.

## KafkaConsumerImpl.java

Este código representa a implementação de uma classe consumidora Kafka chamada `KafkaConsumerImpl`. Ele escuta dois tópicos do Kafka, `transacao.request.topic` e `callback.request.topic`, e processa as mensagens recebidas. Ele usa a classe `ObjectMapper` da biblioteca Jackson para desserializar as mensagens JSON em objetos Java. Também usa a classe `JavaMailSender` do Spring Framework para enviar e-mails e a classe `RestTemplate` para fazer solicitações HTTP.

### Exemplo de Uso

```java
KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl(objectMapper, mailSender, restTemplate);
kafkaConsumer.processarMensagemTransacaoCliente(conteudo);
kafkaConsumer.enviarEmailAoUsuario(destinatario, assunto, corpo);
kafkaConsumer.processarMensagemTransacaoEmpresa(conteudo);
kafkaConsumer.enviarCallbackEmpresa(url, mensagem);
```

### Análise de Código

#### Principais funcionalidades

- Escuta o tópico Kafka `transacao.request.topic` e processa as mensagens recebidas, convertendo-as de JSON para um objeto `EmailDTO` e enviando um e-mail para o destinatário especificado.
- Escuta o tópico Kafka `callback.request.topic` e processa as mensagens recebidas, convertendo-as de JSON para um objeto `CallbackDTO` e fazendo uma solicitação HTTP POST para a URL especificada.
- Usa a classe `ObjectMapper` para desserializar mensagens JSON em objetos Java.
- Usa a classe `JavaMailSender` para enviar e-mails.
- Usa a classe `RestTemplate` para fazer solicitações HTTP.

#### Métodos

- `processarMensagemTransacaoCliente(String conteudo)`: Processa a mensagem recebida do tópico Kafka transacao.request.topic. Converte a mensagem JSON em um objeto EmailDTO e extrai o destinatário, assunto e corpo do e-mail. Em seguida, chama o método enviarEmailAoUsuario para enviar o e-mail.
- `enviarEmailAoUsuario(String destinatario, String assunto, String corpo)`: Envia um e-mail para o destinatário especificado usando a classe JavaMailSender. Cria um objeto MimeMessage, define o destinatário, assunto e corpo do e-mail e o envia.
- `processarMensagemTransacaoEmpresa(String conteudo)`: Processa a mensagem recebida do tópico Kafka callback.request.topic. Converte a mensagem JSON em um objeto CallbackDTO e extrai a URL e a mensagem. Em seguida, chama o método enviarCallbackEmpresa para fazer uma solicitação HTTP POST para a URL especificada.
- `enviarCallbackEmpresa(String url, String mensagem)`: Faz uma solicitação HTTP POST para a URL especificada usando a classe RestTemplate. Define os cabeçalhos da solicitação, corpo e método e envia a solicitação. Se o código de status da resposta for bem-sucedido (2xx), registra a mensagem de sucesso. Caso contrário, lança uma exceção NotificacaoEmpresaException com o código de status da resposta.

#### Campos

- `objectMapper`: Uma instância da classe ObjectMapper da biblioteca Jackson usada para serialização e desserialização JSON.
- `mailSender`: Uma instância da classe JavaMailSender do Spring Framework usada para enviar e-mails.
- `restTemplate`: Uma instância da classe RestTemplate usada para fazer solicitações HTTP.

<br>
<br>



<p align="left"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Infra.md"><<< Anterior: Infra</a></p>
<p align="right"><a href="https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/documentacao/Sumarios%20Das%20Classes/Notification.md">Próximo: Notification >>></a></p>

