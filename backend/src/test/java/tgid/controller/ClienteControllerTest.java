package tgid.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.ClienteDTO;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.ClienteRegistroException;
import tgid.exception.ClienteRemocaoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteControllerTest {

    @Mock
    private CPFValidator cpfValidator;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    // pode cadastrar um novo cliente com CPF válido
    @Test
    public void testRegistrarNovoClienteComCpfValido() {
        // Arranjo
        ClienteDTO cliente = new ClienteDTO();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Mockar o método isValid de cpfValidator para retornar true
        when(cpfValidator.isValid(cliente.getCpf(), null)).thenReturn(true);

        // Agir
        ResponseEntity<?> response = clienteController.registrarCliente(cliente);

        // Afirmar
        assertEquals(ResponseEntity.ok().body("Cadastro realizado com sucesso!"), response);
        verify(clienteService, times(1)).registrarCliente(cliente.getCpf(), cliente.getNome(), cliente.getEmail(), cliente.getSaldo());
    }

    // pode listar todos os clientes
    @Test
    public void testListarTodosClientes() {
        // Arranjo
        List<ClienteDTO> expectedClients = new ArrayList<>();
        expectedClients.add(new ClienteDTO());
        expectedClients.add(new ClienteDTO());

        // Mockar o método listarTodosClientes de clienteService para retornar os clientes esperados
        when(clienteService.listarTodosClientes()).thenReturn(expectedClients);

        // Agir
        List<ClienteDTO> clients = clienteController.listarTodosClientes();

        // Afirmar
        assertEquals(expectedClients, clients);
    }

    // pode deletar um cliente pelo ID
    @Test
    public void testDeletarClientePeloId() {
        // Arranjo
        Long clientId = 1L;

        // Agir
        ResponseEntity<?> response = clienteController.deleteCliente(clientId);

        // Afirmar
        assertEquals(ResponseEntity.ok("Cliente deletado com sucesso!"), response);
        verify(clienteService, times(1)).deleteCliente(clientId);
    }

    // lança exceção ao cadastrar cliente com CPF inválido
    @Test
    public void testRegistrarClienteComCpfInvalidoLancaExcecao() {
        // Arranjo
        ClienteDTO cliente = new ClienteDTO();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Mockar o método isValid de cpfValidator para retornar false
        when(cpfValidator.isValid(cliente.getCpf(), null)).thenReturn(false);

        // Agir e Afirmar
        assertThrows(ClienteRegistroException.class, () -> clienteController.registrarCliente(cliente));
    }

    // lança exceção ao listar clientes e ocorre um erro
    @Test
    public void testListarClientesLancaExcecaoQuandoOcorreErro() {
        // Mockar o método listarTodosClientes de clienteService para lançar uma exceção
        when(clienteService.listarTodosClientes()).thenThrow(new RuntimeException("Error"));

        // Agir e Afirmar
        assertThrows(ClienteNaoEncontradoException.class, clienteController::listarTodosClientes);
    }

    // lança exceção ao excluir um cliente e há um erro
    @Test
    public void testDeletarClienteLancaExcecaoQuandoHaErro() {
        // Arranjo
        Long clientId = 1L;

        // Mockar o método deleteCliente de clienteService para lançar uma exceção
        doThrow(new RuntimeException("Error")).when(clienteService).deleteCliente(clientId);

        // Agir e Afirmar
        assertThrows(ClienteRemocaoException.class, () -> clienteController.deleteCliente(clientId));
    }
}
