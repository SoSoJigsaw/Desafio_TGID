package tgid.entity;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SpringBootTest
public class ClienteTests {

    // Criar uma nova instância de Cliente com CPF, nome, email e saldo válidos
    @Test
    public void test_criar_novo_cliente_com_dados_validos() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act and Assert
        assertNotNull(cliente);
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("John Doe", cliente.getNome());
        assertEquals("johndoe@example.com", cliente.getEmail());
        assertEquals(100.0, cliente.getSaldo(), 0.001);
    }

    // Obter o ID de uma instância existente de Cliente
    @Test
    public void test_obter_id_de_cliente_existente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        // Act and Assert
        assertNotNull(cliente.getId());
        assertEquals(1L, cliente.getId().longValue());
    }

    // Atualizar CPF, nome, email e saldo de uma instância existente de Cliente
    @Test
    public void test_atualizar_dados_de_cliente_existente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act
        cliente.setCpf("98765432109");
        cliente.setNome("Jane Smith");
        cliente.setEmail("janesmith@example.com");
        cliente.setSaldo(200.0);

        // Assert
        assertEquals("98765432109", cliente.getCpf());
        assertEquals("Jane Smith", cliente.getNome());
        assertEquals("janesmith@example.com", cliente.getEmail());
        assertEquals(200.0, cliente.getSaldo(), 0.001);
    }

    // Criar uma nova instância de Cliente com CPF nulo
    @Test
    public void test_criar_novo_cliente_com_cpf_nulo() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf(null);
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act and Assert
        assertNotNull(cliente.getCpf());
        assertEquals("John Doe", cliente.getNome());
        assertEquals("johndoe@example.com", cliente.getEmail());
        assertEquals(100.0, cliente.getSaldo(), 0.001);
    }

    // Criar uma nova instância de Cliente com nome nulo
    @Test
    public void test_criar_novo_cliente_com_nome_nulo() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome(null);
        cliente.setEmail("johndoe@example.com");
        cliente.setSaldo(100.0);

        // Act and Assert
        assertEquals("12345678901", cliente.getCpf());
        assertNotNull(cliente.getNome());
        assertEquals("johndoe@example.com", cliente.getEmail());
        assertEquals(100.0, cliente.getSaldo(), 0.001);
    }

    // Criar uma nova instância de Cliente com email nulo
    @Test
    public void test_criar_novo_cliente_com_email_nulo() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("John Doe");
        cliente.setEmail(null);
        cliente.setSaldo(100.0);

        // Act and Assert
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("John Doe", cliente.getNome());
        assertNotNull(cliente.getEmail());
        assertEquals(100.0, cliente.getSaldo(), 0.001);
    }

}
