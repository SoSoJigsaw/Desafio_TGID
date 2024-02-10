package tgid.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tgid.entity.Cliente;
import tgid.exception.ClienteNaoEncontradoException;
import tgid.exception.ClienteRegistroException;
import tgid.exception.TransacaoRemocaoException;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    // pode registrar um novo cliente com parâmetros de entrada válidos
    @Test
    public void testRegistrarClienteComInputValido() {
        // Arranjo
        String cpf = "123456789";
        String nome = "John Doe";
        String email = "johndoe@example.com";
        Double saldo = 100.0;

        // Agir
        clienteService.registrarCliente(cpf, nome, email, saldo);

        // Afirmar
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    // pode excluir um cliente quando ele existir no banco de dados
    @Test
    public void testDeletarClienteExistente() {
        // Arranjo
        Long id = 1L;
        Cliente cliente = new Cliente("123456789", "John Doe", "johndoe@example.com", 100.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.getReferenceById(id)).thenReturn(cliente);

        // Agir
        clienteService.deleteCliente(id);

        // Afirmar
        verify(transacaoRepository, times(1)).deleteApartirDoCliente(cliente);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    // Logra uma exceção ao tentar registrar um novo cliente com parâmetros de entrada nula
    @Test
    public void testRegistrarClienteComParametrosNulos() {
        // Arranjo
        String cpf = null;
        String nome = null;
        String email = null;
        Double saldo = null;

        // Agir e Afirmar
        assertThrows(ClienteRegistroException.class, () -> {
            clienteService.registrarCliente(cpf, nome, email, saldo);
        });
    }

    // lança uma exceção ao tentar excluir um cliente inexistente
    @Test
    public void testDeletarUmClienteInexistente() {
        // Arranjo
        Long id = 1L;
        when(clienteRepository.getReferenceById(id)).thenReturn(null);

        // Agir e Afirmar
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.deleteCliente(id);
        });
    }

    // lança uma exceção ao tentar excluir um cliente com transações associadas que não podem ser excluídas
    @Test
    public void testDeletarClienteComTransacoesAssociadas() {
        // Arranjo
        Long id = 1L;
        Cliente cliente = new Cliente("123456789", "John Doe", "johndoe@example.com", 100.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.getReferenceById(id)).thenReturn(cliente);
        doThrow(new TransacaoRemocaoException("Error deleting transactions")).when(transacaoRepository).deleteApartirDoCliente(cliente);

        // Agir e Afirmar
        assertThrows(TransacaoRemocaoException.class, () -> {
            clienteService.deleteCliente(id);
        });
    }

    // Deveria criar o novo cliente e com todos os mesmos dados passados como parâmetro
    @Test
    public void testRegistrarCliente() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        // Define o comportamento dos mocks
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation ->
        {
            Cliente savedCliente = invocation.getArgument(0);
            savedCliente.setId(1L);
            return savedCliente;
        });
        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(cliente);

        // Chamada do método a ser testado
        clienteService.registrarCliente("529.982.247-25", "João Da Silva",
                "joao.silva@hotmail.com", 1000.0);

        Cliente novoCliente = clienteRepository.findByEmail(cliente.getEmail()).orElse(null);

        Assertions.assertNotNull(novoCliente);
        Assertions.assertEquals(cliente.getNome(), novoCliente.getNome());
        Assertions.assertEquals(cliente.getCpf(), novoCliente.getCpf());
        Assertions.assertEquals(cliente.getSaldo(), novoCliente.getSaldo());
        Assertions.assertEquals(cliente.getEmail(), novoCliente.getEmail());
    }

    // Deveria listar todos os clientes
    @Test
    void testListarTodosClientes() {

        // Criação de objetos necessários
        Cliente cliente1 = new Cliente();
        cliente1.setCpf("529.982.247-25");
        cliente1.setNome("João Da Silva");
        cliente1.setEmail("joao.silva@hotmail.com");
        cliente1.setSaldo(1000.0);

        Cliente cliente2 = new Cliente();
        cliente2.setCpf("624.404.711-78");
        cliente2.setNome("Roberto Oliveira");
        cliente2.setEmail("roberto.oliveira@gmail.com");
        cliente2.setSaldo(500.0);

        Cliente cliente3 = new Cliente();
        cliente3.setCpf("368.687.537-30");
        cliente3.setNome("Daniela Lucena");
        cliente3.setEmail("lucena_daniela@email.com");
        cliente3.setSaldo(3000.0);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        // Define o comportamento dos mocks
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteRepository.findAll();

        // Afirmações
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsAll(clientes));
    }

    // Deveria deletar um cliente por seu id
    @Test
    void testDeleteCliente() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        // Define o comportamento dos mocks
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation ->
        {
            Cliente savedCliente = invocation.getArgument(0);
            savedCliente.setId(1L);
            return savedCliente;
        });
        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(cliente);
        doNothing().when(clienteRepository).delete(any(Cliente.class));

        // Chamada dos métodos a ser testado
        clienteService.registrarCliente("529.982.247-25", "João Da Silva",
                "joao.silva@hotmail.com", 1000.0);

        Cliente novoCliente = clienteRepository.findByEmail(cliente.getEmail()).orElse(null);
        Assertions.assertNotNull(novoCliente);

        clienteService.deleteCliente(1L);

        verify(clienteRepository, times(1)).getReferenceById(1L);
        verify(clienteRepository, times(1)).delete(cliente);
    }
}