package tgid.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteTest {

    // Criar uma nova instância de Cliente com CPF, nome, email e saldo válidos
    @Test
    public void testCriarNovoClienteComDadosValidos() {
        // Arranjo
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Agir e Afirmar
        assertNotNull(cliente);
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("John Doe", cliente.getNome());
        assertEquals("johndoe@example.com", cliente.getEmail());
        assertEquals(100.0, cliente.getSaldo(), 0.001);
    }

    // Obter o ID de uma instância existente de Cliente
    @Test
    public void testObterIdDeClienteExistente() {
        // Arranjo
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        // Agir e Afirmar
        assertNotNull(cliente.getId());
        assertEquals(1L, cliente.getId().longValue());
    }

    // Atualizar CPF, nome, email e saldo de uma instância existente de Cliente
    @Test
    public void testAtualizarDadosDeClienteExistente() {
        // Arranjo
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Agir
        cliente.setCpf("98765432109");
        cliente.setNome("Jane Smith");
        cliente.setEmail("janesmith@example.com");
        cliente.setSaldo(200.0);

        // Afirmar
        assertEquals("98765432109", cliente.getCpf());
        assertEquals("Jane Smith", cliente.getNome());
        assertEquals("janesmith@example.com", cliente.getEmail());
        assertEquals(200.0, cliente.getSaldo(), 0.001);
    }

}
