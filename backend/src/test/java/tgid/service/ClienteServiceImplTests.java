package tgid.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tgid.entity.Cliente;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestConfiguration
@Import(ClienteServiceImpl.class)
public class ClienteServiceImplTests {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoRepository transacaoRepository;


    // Pode criar um novo Cliente com parâmetros válidos
    @Test
    @Transactional
    public void test_createClienteWithValidParameters() {
        // Arrange
        String cpf = "12345678901";
        String nome = "John Doe";
        String email = "johndoe@example.com";
        Double saldo = 100.0;

        // Act
        clienteService.registrarCliente(cpf, nome, email, saldo);

        // Assert
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    // Pode listar todos os Clientes existentes
    @Test
    @Transactional
    public void test_listAllClientes() {
        // Arrange
        List<Cliente> expectedClientes = new ArrayList<>();
        expectedClientes.add(new Cliente());
        expectedClientes.add(new Cliente());
        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        // Act
        List<Cliente> actualClientes = clienteService.listarTodosClientes();

        // Assert
        assertEquals(expectedClientes, actualClientes);
    }

    // Pode excluir um Cliente existente e todas as Transacoes relacionadas
    @Test
    @Transactional
    public void test_deleteExistingClienteAndRelatedTransacoes() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        when(clienteRepository.getReferenceById(clienteId)).thenReturn(cliente);

        // Act
        clienteService.deleteCliente(clienteId);

        // Assert
        verify(transacaoRepository, times(1)).deleteApartirDoCliente(cliente);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    // Não pode criar um novo Cliente com parâmetros inválidos (por exemplo, cpf vazio)
    @Test
    @Transactional
    public void test_cannotCreateClienteWithInvalidParameters() {
        // Arrange
        String cpf = "";
        String nome = "John Doe";
        String email = "johndoe@example.com";
        Double saldo = 100.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registrarCliente(cpf, nome, email, saldo);
        });
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    // Pode excluir um Cliente sem Transacoes relacionadas sem erros
    @Test
    @Transactional
    public void test_deleteClienteWithNoRelatedTransacoesWithoutErrors() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        when(clienteRepository.getReferenceById(clienteId)).thenReturn(cliente);

        // Act
        clienteService.deleteCliente(clienteId);

        // Assert
        verify(transacaoRepository, times(1)).deleteApartirDoCliente(cliente);
        verify(clienteRepository, times(1)).delete(cliente);
    }

}
