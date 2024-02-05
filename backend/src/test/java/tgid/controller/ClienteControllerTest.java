package tgid.controller;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.entity.Cliente;
import tgid.exception.CpfInvalidoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    // Deve listar com sucesso todos os clientes
    @Test
    public void test_listar_todos_os_clientes() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        List<Cliente> expectedClients = new ArrayList<>();
        expectedClients.add(new Cliente());
        expectedClients.add(new Cliente());

        // Simular o método listarTodosClientes do clienteService para retornar os clientes esperados
        when(clienteService.listarTodosClientes()).thenReturn(expectedClients);

        // Act
        List<Cliente> clients = clienteController.listarTodosClientes();

        // Assert
        assertEquals(expectedClients, clients);
    }

    // Deve deletar com sucesso um cliente pelo ID
    @Test
    public void test_deletar_cliente_por_id() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Long clientId = 1L;

        // Act
        ResponseEntity<?> response = clienteController.deleteCliente(clientId);

        // Assert
        assertEquals(ResponseEntity.ok("Cliente deletado com sucesso!"), response);
        verify(clienteService, times(1)).deleteCliente(clientId);
    }

    // Deve lançar exceção ao registrar um novo cliente com CPF nulo
    @Test
    public void test_lancar_excecao_ao_registrar_novo_cliente_com_cpf_nulo() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Cliente cliente = new Cliente();
        cliente.setCpf(null);
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Simular o método isValid do cpfValidator para retornar false
        when(cpfValidator.isValid(anyString(), any(ConstraintValidatorContext.class))).thenReturn(false);

        ResponseEntity<?> response = clienteController.registrarCliente(cliente);

        // Act and Assert
        assertEquals(ResponseEntity.badRequest().body("CPF Inválido"), response);
    }

}
