package tgid.controller;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tgid.dto.ClienteDTO;
import tgid.entity.Cliente;
import tgid.exception.ClienteRegistroException;
import tgid.exception.CpfInvalidoException;
import tgid.service.ClienteService;
import tgid.validation.CPFValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    // Deve listar com sucesso todos os clientes
    @Test
    public void testListarTodosClientes() {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        List<ClienteDTO> expectedClients = new ArrayList<>();
        expectedClients.add(new ClienteDTO());
        expectedClients.add(new ClienteDTO());

        // Simular o método listarTodosClientes do clienteService para retornar os clientes esperados
        when(clienteService.listarTodosClientes()).thenReturn(expectedClients);

        // Act
        List<ClienteDTO> clients = clienteController.listarTodosClientes();

        // Assert
        assertEquals(expectedClients, clients);
    }

    // Deve deletar com sucesso um cliente pelo ID
    @Test
    public void testDeletarClientePorId() {
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

    // Deve ser incapaz de registrar um novo cliente com CPF nulo
    @Test
    public void testRegistrarNovoClienteComCpfNulo() throws Exception {
        // Arrange
        ClienteService clienteService = mock(ClienteService.class);
        CPFValidator cpfValidator = mock(CPFValidator.class);
        ClienteController clienteController = new ClienteController(clienteService, cpfValidator);
        ClienteDTO cliente = new ClienteDTO();
        cliente.setCpf(null);
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act and Assert
        assertFalse(cpfValidator.isValid(cliente.getCpf(), null));

    }

}
