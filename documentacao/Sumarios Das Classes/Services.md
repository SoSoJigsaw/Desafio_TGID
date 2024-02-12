# Services

## ClienteServiceImpl.class

A classe `ClienteServiceImpl` é uma implementação da interface `ClienteService`. Ela fornece métodos para registrar clientes, listar todos os clientes e excluir clientes. Além disso, trata exceções relacionadas ao registro e exclusão de clientes.

### Exemplo de Uso

```java
ClienteRepository clienteRepository = new ClienteRepository();
TransacaoRepository transacaoRepository = new TransacaoRepository();
ClienteService clienteService = new ClienteServiceImpl(clienteRepository, transacaoRepository);

// Registrar um novo cliente
clienteService.registrarCliente("12345678901", "John Doe", "johndoe@example.com", 1000.0);

// Listar todos os clientes
List<ClienteDTO> clientes = clienteService.listarTodosClientes();

// Excluir um cliente
clienteService.deleteCliente(1L);
```

### Análise de Código

#### Principais funcionalidades

- Registrar um novo cliente com CPF único, nome, email e saldo positivo.
- Listar todos os clientes registrados.
- Excluir um cliente pelo seu ID, juntamente com suas transações associadas.

#### Métodos

- `registrarCliente(String cpf, String nome, String email, Double saldo)`: Registra um novo cliente com o CPF, nome, email e saldo fornecidos. Se o saldo for negativo, uma exceção SaldoNegativoException é lançada. Se o CPF já estiver em uso por outro cliente, uma exceção ViolacaoConstraintCpfException é lançada. Se algum dos parâmetros de entrada for nulo, uma exceção ClienteRegistroException é lançada.
- `listarTodosClientes()`: Lista todos os clientes registrados como objetos `ClienteDTO`. Se nenhum cliente for encontrado, uma exceção `ClienteNaoEncontradoException` é lançada.
- `deleteCliente(Long id)`: Exclui um cliente e suas transações associadas pelo seu ID. Se o cliente não for encontrado, uma exceção `ClienteNaoEncontradoException` é lançada. Se houver um erro ao excluir as transações, uma exceção `TransacaoRemocaoException` é lançada.

#### Campos

- `clienteRepository`: Uma instância de ClienteRepository usada para acessar e manipular dados de clientes.
- `transacaoRepository`: Uma instância de TransacaoRepository usada para acessar e manipular dados de transações.

## EmpresaServiceImpl.class

A classe `EmpresaServiceImpl` é uma implementação da interface `EmpresaService`. Ela fornece métodos para registrar uma nova empresa, alterar as taxas de imposto de uma empresa, listar todas as empresas registradas e excluir uma empresa.

### Exemplo de Uso

```java
EmpresaServiceImpl empresaService = new EmpresaServiceImpl(empresaRepository, transacaoRepository);

// Registrar uma nova empresa
empresaService.registrarEmpresa("12345678901234", "Empresa A", 1000.0, 0.05, 0.1);

// Alterar a taxa de depósito de uma empresa
empresaService.mudarTaxaValorEmpresa(1L, "DEPÓSITO", 0.03);

// Listar todas as empresas registradas
List<EmpresaDTO> empresas = empresaService.listarTodasEmpresas();

// Excluir uma empresa
empresaService.deleteEmpresa(1L);
```

### Análise de Código

#### Principais funcionalidades

- Registrar uma nova empresa com um CNPJ único, nome, saldo inicial, taxa de depósito e taxa de saque.
- Alterar a taxa de depósito ou de saque de uma empresa.
- Listar todas as empresas registradas.
- Excluir uma empresa e suas transações associadas.

#### Métodos

- `registrarEmpresa(String cnpj, String nome, Double saldo, Double taxaDeposito, Double taxaSaque)`: Registra uma nova empresa com o CNPJ, nome, saldo inicial, taxa de depósito e taxa de saque fornecidos. Lança exceções se os valores fornecidos forem inválidos ou se o CNPJ já estiver sendo usado por outra empresa.
- `mudarTaxaValorEmpresa(Long empresaId, String tipoTaxa, Double valor)`: Altera a taxa de depósito ou de saque de uma empresa com o ID fornecido. Lança uma exceção se a empresa não existir ou se o valor fornecido for nulo.
- `listarTodasEmpresas()`: Lista todas as empresas registradas e retorna uma lista de objetos `EmpresaDTO` contendo suas informações. Lança uma exceção se não houver empresas registradas.
- `deleteEmpresa(Long id)`: Exclui uma empresa com o ID fornecido. Também exclui todas as transações associadas à empresa. Lança uma exceção se a empresa não existir ou se houver um erro ao excluir as transações.

#### Campos

- `empresaRepository`: Uma instância de EmpresaRepository usada para acessar e manipular dados da empresa no banco de dados.
- `transacaoRepository`: Uma instância de TransacaoRepository usada para acessar e manipular dados de transações no banco de dados.

## TransacaoServiceImpl.java

A classe `TransacaoServiceImpl` é responsável por implementar a interface `TransacaoService` e fornecer a funcionalidade para realizar operações de depósito, saque e listagem de transações. Também lida com a lógica para atualizar saldos, enviar notificações e gerar callbacks.

### Exemplo de Uso

```java
TransacaoServiceImpl transacaoService = new TransacaoServiceImpl(transacaoRepository, empresaRepository, clienteRepository, calcularTaxa, notificacaoEmpresa, notificacaoCliente);
ResponseEntity<?> response = transacaoService.realizarDeposito(1L, 1L, 100.0);
```

### Análise de Código

#### Principais funcionalidades

- Realizar operação de depósito: O método realizarDeposito recebe os IDs da empresa e do cliente, além do valor do depósito, e realiza a transação de depósito. Calcula a taxa de depósito, verifica se o cliente tem saldo suficiente, atualiza os saldos do cliente e da empresa e registra a transação. Também envia notificações e callbacks em caso de sucesso ou falha.
- Realizar operação de saque: O método realizarSaque recebe os IDs da empresa e do cliente, além do valor do saque, e realiza a transação de saque. Calcula a taxa de saque, verifica se a empresa tem saldo suficiente, atualiza os saldos do cliente e da empresa e registra a transação. Também envia notificações e callbacks em caso de sucesso ou falha.
- Listar todas as transações: O método listarTodasTransacoes recupera todas as transações do banco de dados e retorna uma lista de objetos TransacaoDTO contendo os detalhes da transação.
- Excluir uma transação: O método deleteTransacao exclui uma transação do banco de dados com base no ID da transação fornecido.

#### Métodos

- `realizarDeposito`: Realiza uma transação de depósito calculando a taxa de depósito, verificando o saldo do cliente, atualizando os saldos, registrando a transação e enviando notificações e callbacks.
- `realizarSaque`: Realiza uma transação de saque calculando a taxa de saque, verificando o saldo da empresa, atualizando os saldos, registrando a transação e enviando notificações e callbacks.
- `listarTodasTransacoes`: Recupera todas as transações do banco de dados e retorna uma lista de objetos `TransacaoDTO`.
- `deleteTransacao`: Exclui uma transação do banco de dados com base no ID da transação fornecido.

#### Campos

- `transacaoRepository`: Repositório para acessar dados de transação no banco de dados.
- `empresaRepository`: Repositório para acessar dados da empresa no banco de dados.
- `clienteRepository`: Repositório para acessar dados do cliente no banco de dados.
- `calcularTaxa`: Serviço para calcular taxas de transação.
- `notificacaoEmpresa`: Serviço para enviar notificações para empresas.
- `notificacaoCliente`: Serviço para enviar notificações para clientes.