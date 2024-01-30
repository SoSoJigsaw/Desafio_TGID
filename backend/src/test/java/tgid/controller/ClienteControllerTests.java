package tgid.controller;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.entity.Cliente;
import tgid.exception.objetosExceptions.CpfInvalidoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTests {

    // Should successfully register a new client with valid CPF
    @Test
    public void test_register_new_client_with_valid_cpf() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Mock the isValid method of cpfValidator to return true
        when(cpfValidator.isValid(anyString(), any(ConstraintValidatorContext.class))).thenReturn(true);

        // Act
        ResponseEntity<?> response = clienteController.registrarCliente(cliente);

        // Assert
        assertEquals(ResponseEntity.ok("Cadastro realizado com sucesso!"), response);
        verify(clienteService, times(1))
                .registrarCliente("12345678901", "John Doe", "johndoe@example.com", 100.0);
    }

    // Should successfully list all clients
    @Test
    public void test_list_all_clients() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        List<Cliente> expectedClients = new ArrayList<>();
        expectedClients.add(new Cliente());
        expectedClients.add(new Cliente());

        // Mock the listarTodosClientes method of clienteService to return the expected clients
        when(clienteService.listarTodosClientes()).thenReturn(expectedClients);

        // Act
        List<Cliente> clients = clienteController.listarTodosClientes();

        // Assert
        assertEquals(expectedClients, clients);
    }

    // Should successfully delete a client by ID
    @Test
    public void test_delete_client_by_id() {
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

    // Should throw exception when registering a new client with null CPF
    @Test
    public void test_throw_exception_when_registering_new_client_with_null_cpf() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Cliente cliente = new Cliente();
        cliente.setCpf(null);
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Mock the isValid method of cpfValidator to return false
        when(cpfValidator.isValid(anyString(), any(ConstraintValidatorContext.class))).thenReturn(false);

        // Act and Assert
        assertThrows(CpfInvalidoException.class, () -> clienteController.registrarCliente(cliente));
    }

    // Should throw exception when registering a new client with null name
    @Test
    public void test_throw_exception_when_registering_new_client_with_null_name() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome(null);
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act and Assert
        assertThrows(ConstraintViolationException.class, () -> clienteController.registrarCliente(cliente));
    }

    // Should throw exception when registering a new client with null email
    @Test
    public void test_throw_exception_when_registering_new_client_with_null_email() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail(null);
        cliente.setSaldo(100.0);

        // Act and Assert
        assertThrows(ConstraintViolationException.class, () -> clienteController.registrarCliente(cliente));
    }
}
