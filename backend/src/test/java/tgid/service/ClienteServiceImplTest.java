package tgid.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.entity.Cliente;
import tgid.repository.ClienteRepository;
import tgid.repository.TransacaoRepository;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceImplTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ClienteServiceImpl clienteService;


    // Deveria criar o novo cliente e com todos os mesmos dados passados como parâmetro
    @Test
    public void testRegistrarCliente() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        clienteService.registrarCliente("529.982.247-25", "João Da Silva",
                "joao.silva@hotmail.com", 1000.0);

        Cliente novoCliente = clienteRepository.findByEmail(cliente.getEmail()).orElse(null);

        Assertions.assertNotNull(novoCliente);
        Assertions.assertEquals(cliente.getNome(), novoCliente.getNome());
        Assertions.assertEquals(cliente.getCpf(), novoCliente.getCpf());
        Assertions.assertEquals(cliente.getSaldo(), novoCliente.getSaldo());
        Assertions.assertEquals(cliente.getEmail(), novoCliente.getEmail());

        clienteService.deleteCliente(novoCliente.getId());
    }

    // Deveria listar todos os clientes
    @Test
    void testListarTodosClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        Assertions.assertNotNull(clientes);

        for (Cliente cliente : clientes) {
            Assertions.assertTrue(clienteRepository.findById(cliente.getId()).isPresent());
        }
    }

    // Deveria deletar um cliente por seu id
    @Test
    void testDeleteCliente() {

        Cliente cliente = new Cliente();
        cliente.setCpf("529.982.247-25");
        cliente.setNome("João Da Silva");
        cliente.setEmail("joao.silva@hotmail.com");
        cliente.setSaldo(1000.0);

        clienteService.registrarCliente("529.982.247-25", "João Da Silva",
                "joao.silva@hotmail.com", 1000.0);

        Cliente novoCliente = clienteRepository.findByEmail(cliente.getEmail()).orElse(null);

        clienteService.deleteCliente(novoCliente.getId());

        Cliente clienteDeletado = clienteRepository.findById(novoCliente.getId()).orElse(null);

        Assertions.assertNull(clienteDeletado);

    }
}