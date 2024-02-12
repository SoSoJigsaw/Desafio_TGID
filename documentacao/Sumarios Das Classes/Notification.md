# Notification

## NotificacaoClienteImpl.java

A classe `NotificacaoClienteImpl` é uma implementação de serviço que fornece métodos para enviar notificações para clientes via Kafka. Ela utiliza a interface `KafkaProducer` para enviar mensagens e a classe `EmailDTO` para encapsular os detalhes do e-mail.

### Exemplo de Uso

```java
// Criar uma instância do KafkaProducer
KafkaProducer kafkaProducer = new KafkaProducerImpl();

// Criar uma instância do NotificacaoClienteImpl
NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducer);

// Enviar uma notificação para um cliente via Kafka
notificacaoCliente.enviarNotificacaoKafka("john@example.com", "Confirmação de Pagamento", "Seu pagamento de $100 foi confirmado.");

// Formatar o assunto de uma operação de transação
String assunto = notificacaoCliente.formatAssuntoOperacaoRealizada("Pagamento", 100);

// Formatar o corpo de uma operação de transação
String corpo = notificacaoCliente.formatCorpoOperacaoRealizada("Pagamento", 100, "Empresa ABC", LocalDateTime.now(), 500.0);
```

### Análise de Código

#### Principais funcionalidades

- Envio de notificações para clientes via Kafka.
- Formatação do assunto e corpo das operações de transação.

#### Métodos

- `enviarNotificacaoKafka(String destinatario, String assunto, String corpo)`: Envia uma notificação para um cliente via Kafka. Verifica se o destinatário, assunto e corpo não são nulos ou vazios e lança uma exceção se algum deles for inválido. Cria uma instância de EmailDTO com os detalhes fornecidos e envia usando o kafkaProducer.
- `formatAssuntoOperacaoRealizada(String tipoTransacao, int valorTransacao)`: Formata o assunto de uma operação de transação concatenando o tipo de transação e valor.
- `formatCorpoOperacaoRealizada(String tipoTransacao, int valorTransacao, String nomeEmpresa, LocalDateTime dataTransacao, Double saldo)`: Formata o corpo de uma operação de transação combinando o tipo de transação, valor, nome da empresa, data da transação e saldo. Formata a data da transação usando o DateTimeFormatter e converte o saldo para um inteiro.
- `formatAssuntoOperacaoNegada(String tipoTransacao, int valorTransacao)`: Formata o assunto de uma operação de transação negada concatenando o tipo de transação com a palavra "negada".
- `formatCorpoOperacaoNegadaEmpresa(String tipoTransacao, int valorTransacao, String nomeEmpresa, LocalDateTime dataTransacao, Double saldo)`: Formata o corpo de uma operação de transação negada para uma empresa. Inclui o tipo de transação, valor, nome da empresa, data da transação e saldo. Formata a data da transação usando o DateTimeFormatter e converte o saldo para um inteiro.
- `formatCorpoOperacaoNegadaCliente(String tipoTransacao, int valorTransacao, String nomeEmpresa, LocalDateTime dataTransacao, Double saldo)`: Formata o corpo de uma operação de transação negada para um cliente. Inclui o tipo de transação, valor, nome da empresa, data da transação e saldo. Formata a data da transação usando o DateTimeFormatter e converte o saldo para um inteiro.

#### Campos

- `kafkaProducer`: Uma instância da interface KafkaProducer usada para enviar mensagens para o Kafka.

## NotificacaoEmpresaImpl.class

A classe `NotificacaoEmpresaImpl` é uma implementação da interface `NotificacaoEmpresa`. Ela fornece métodos para enviar callbacks para um tópico Kafka e formatar mensagens de callback.

### Exemplo de Uso

```java
NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(kafkaProducer);
notificacaoEmpresa.enviarCallbackKafka("https://example.com/callback", "Mensagem de Callback");
String mensagemCallback = notificacaoEmpresa.formatCallbackSucesso("Taxa 1", 100, "John Doe", "Empresa ABC", LocalDateTime.now(), 500.0);
```

### Análise de Código

#### Principais funcionalidades

- Envio de callbacks para um tópico Kafka
- Formatação de mensagens de callback

#### Métodos

- `enviarCallbackKafka(String url, String mensagem)`: Envia uma mensagem de callback para um tópico Kafka. Verifica se a URL e a mensagem não são nulas ou vazias e lança uma exceção se forem.
- `formatCallbackSucesso(String taxaNome, int valor, String clienteNome, String empresaNome, LocalDateTime dataTransacao, Double saldo)`: Formata uma mensagem de callback bem-sucedida com os parâmetros fornecidos.
- `formatCallbackFalhaEmpresa(String taxaNome, int valor, String clienteNome, String empresaNome, LocalDateTime dataTransacao, Double saldo, Double saldoVirtual)`: Formata uma mensagem de callback para uma transação falha devido a saldo insuficiente na conta da empresa.
- `formatCallbackFalhaCliente(String taxaNome, int valor, String clienteNome, String empresaNome, LocalDateTime dataTransacao, Double saldo, Double saldoVirtual)`: Formata uma mensagem de callback para uma transação falha devido a saldo insuficiente na conta do cliente.

#### Campos

- `kafkaProducer`: Uma instância da interface KafkaProducer usada para enviar mensagens para um tópico Kafka.