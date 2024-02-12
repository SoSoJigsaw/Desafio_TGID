# Controllers

## <p align="center"> Índices </p>

<table style="margin-left:auto; margin-right:auto;">
    <tr>
        <td>
            <table>
                <thead>
                    <tr><th>Nesse Documento</th></tr>
                </thead>
                <tbody>
                    <tr><td></td></tr>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <thead>
                    <tr><th>Sumários</th></tr>
                </thead>
                <tbody>
                    <tr><td>[Controllers]()</td></tr>
                    <tr><td>[DTOs]()</td></tr>
                    <tr><td>[Entities]()</td></tr>
                    <tr><td>[Exceptions]()</td></tr>
                    <tr><td>[Infra]()</td></tr>
                    <tr><td>[Kafka]()</td></tr>
                    <tr><td>[Notification]()</td></tr>
                    <tr><td>[Repositories]()</td></tr>
                    <tr><td>[Services]()</td></tr>
                    <tr><td>[Util]()</td></tr>
                    <tr><td>[Validation]()</td></tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>

## ClienteController.class

A classe `ClienteController` é um controlador em uma aplicação Java Spring que lida com requisições HTTP relacionadas a clientes. Ela fornece endpoints para registrar um novo cliente, listar todos os clientes e deletar um cliente.

### Exemplo de Uso

```java
ClienteController clienteController = new ClienteController(clienteService, cpfValidator);

// Registrar um novo cliente
ClienteDTO cliente = new ClienteDTO(1, "12345678901", "John Doe", "johndoe@example.com", 100.0);
ResponseEntity<?> response = clienteController.registrarCliente(cliente);
System.out.println(response.getBody()); // Saída: "Cadastro realizado com sucesso!"

// Listar todos os clientes
List<ClienteDTO> clientes = clienteController.listarTodosClientes();
for (ClienteDTO c : clientes) {
    System.out.println(c.getNome());
}

// Deletar um cliente
ResponseEntity<?> response = clienteController.deleteCliente(1);
System.out.println(response.getBody()); // Saída: "Cliente deletado com sucesso!"
```

### Análise de Código

#### Principais Funcionalidades

- Registrar um novo cliente validando o CPF do cliente e chamando o método `registrarCliente` da interface `ClienteService`.
- Listar todos os clientes chamando o método `listarTodosClientes` da interface `ClienteService`.
- Deletar um cliente chamando o método `deleteCliente` da interface `ClienteService`.

#### Métodos

- `registrarCliente`: Manipula a requisição POST HTTP para registrar um novo cliente. Valida o CPF do cliente usando o CPFValidator e chama o método registrarCliente da interface ClienteService para registrar o cliente.
- `listarTodosClientes`: Manipula a requisição GET HTTP para listar todos os clientes. Chama o método listarTodosClientes da interface ClienteService para recuperar a lista de clientes.
- `deleteCliente`: Manipula a requisição DELETE HTTP para deletar um cliente. Chama o método deleteCliente da interface ClienteService para deletar o cliente.

#### Campos

- `clienteService`: Uma instância da interface ClienteService que é usada para interagir com os dados do cliente.
- `cpfValidator`: Uma instância da classe CPFValidator que é usada para validar o CPF de um cliente.

## EmpresaController.class

A classe `EmpresaController` é uma classe de controle em uma aplicação Java Spring que lida com requisições HTTP relacionadas à gestão e interação com dados de empresa. Ela fornece endpoints para registrar uma nova empresa, atualizar a taxa associada a uma empresa, listar todas as empresas e excluir uma empresa.

### Exemplo de Uso

```java
// Criar uma nova instância de EmpresaController
EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);

// Registrar uma nova empresa
EmpresaDTO empresa = new EmpresaDTO();
empresa.setCnpj("12345678901234");
empresa.setNome("Empresa ABC");
empresa.setSaldo(1000.0);
empresa.setTaxaDeposito(0.1);
empresa.setTaxaSaque(0.2);
ResponseEntity<?> response = empresaController.registrarEmpresa(empresa);
System.out.println(response.getBody()); // "Cadastro realizado com sucesso!"

// Atualizar a taxa de uma empresa existente
TaxaDTO taxaDTO = new TaxaDTO();
taxaDTO.setId(1L);
taxaDTO.setTipoTaxa("DEPÓSITO");
taxaDTO.setValor(0.2);
ResponseEntity<?> response = empresaController.mudarTaxaValorEmpresa(taxaDTO);
System.out.println(response.getBody()); // "Taxa associada com sucesso"

// Listar todas as empresas
List<EmpresaDTO> empresas = empresaController.listarTodasEmpresas();
for (EmpresaDTO empresa : empresas) {
    System.out.println(empresa.getNome());
}

// Deletar uma empresa
ResponseEntity<?> response = empresaController.deleteEmpresa(1L);
System.out.println(response.getBody()); // "Empresa deletada com sucesso!"
```

### Análise de Código

#### Principais Funcionalidades

- Registrar uma nova empresa validando o CNPJ e chamando o método `registrarEmpresa` da interface `EmpresaService`.
- Atualizar a taxa associada a uma empresa existente validando o tipo de taxa e chamando o método `mudarTaxaValorEmpresa` da interface `EmpresaService`.
- Listar todas as empresas chamando o método `listarTodasEmpresas` da interface `EmpresaService`.
- Excluir uma empresa chamando o método `deleteEmpresa` da interface `EmpresaService`.

#### Métodos

- `registrarEmpresa`: Manipula a requisição POST HTTP para registrar uma nova empresa. Valida o CNPJ da empresa usando o CNPJValidator e chama o método registrarEmpresa da interface EmpresaService.
- `mudarTaxaValorEmpresa`: Manipula a requisição POST HTTP para atualizar a taxa de uma empresa existente. Valida o tipo de taxa usando o TaxaValidator e chama o método mudarTaxaValorEmpresa da interface EmpresaService.
- `listarTodasEmpresas`: Manipula a requisição GET HTTP para listar todas as empresas. Chama o método listarTodasEmpresas da interface EmpresaService.
- `deleteEmpresa`: Manipula a requisição DELETE HTTP para excluir uma empresa. Chama o método deleteEmpresa da interface EmpresaService.

#### Campos

- `empresaService`: Uma instância da interface EmpresaService que fornece métodos para gerenciar empresas.
- `cnpjValidator`: Uma instância da classe CNPJValidator que valida números de CNPJ.
- `taxaValidator`: Uma instância da classe TaxaValidator que valida tipos de taxa.

## TransacaoController.java

A classe `TransacaoController` é uma classe de controle que lida com requisições HTTP relacionadas a transações. Ela contém métodos para depositar, sacar, listar e excluir transações. Também trata exceções relacionadas a transações inválidas.

### Exemplo de Uso

```java
TransacaoController transacaoController = new TransacaoController(transacaoService);
ResponseEntity<?> response = transacaoController.deposito(1L, 1L, 100.0);
```

### Análise de Código

#### Principais Funcionalidades

- Lidar com requisições HTTP para depositar e sacar transações.
- Lidar com exceções relacionadas a transações inválidas.
- Listar todas as transações.
- Excluir uma transação.

#### Métodos

- `deposito(empresaId, clienteId, valor)`: Manipula a requisição POST HTTP para depositar uma transação. Verifica se o valor é negativo ou zero e lança exceções conforme necessário. Em seguida, chama o método realizarDeposito da interface TransacaoService para realizar o depósito.
- `saque(empresaId, clienteId, valor)`: Manipula a requisição POST HTTP para sacar uma transação. Verifica se o valor é negativo ou zero e lança exceções conforme necessário. Em seguida, chama o método realizarSaque da interface TransacaoService para realizar o saque.
- `listarTodasTransacoes()`: Manipula a requisição GET HTTP para listar todas as transações. Chama o método listarTodasTransacoes da interface TransacaoService para recuperar a lista de transações.
- `deleteTransacao(id)`: Manipula a requisição DELETE HTTP para excluir uma transação. Chama o método deleteTransacao da interface TransacaoService para excluir a transação.

#### Campos

- `transacaoService`: Uma instância da interface TransacaoService usada para realizar operações relacionadas a transações.