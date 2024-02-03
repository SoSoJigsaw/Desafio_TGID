package tgid.entity;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@SpringBootTest
public class TransacaoTests {

    // Criar uma transação com um 'tipo', 'valor', 'dataTransacao', 'cliente' e 'empresa' válidos
    @Test
    public void test_criar_transacao_valida() {
        Transacao transacao = new Transacao();
        transacao.setTipo("valid_tipo");
        transacao.setValor(100.0);
        transacao.setDataTransacao(LocalDateTime.now());
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        transacao.setCliente(cliente);
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        transacao.setEmpresa(empresa);

        assertEquals("valid_tipo", transacao.getTipo());
        assertEquals(100.0, transacao.getValor(), 0.001);
        assertNotNull(transacao.getDataTransacao());
        assertEquals(cliente, transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
    }

    // Criar uma transação com uma 'dataTransacao' no passado
    @Test
    public void test_criar_transacao_com_data_passada() {
        Transacao transacao = new Transacao();
        transacao.setTipo("tipo");
        transacao.setValor(100.0);
        transacao.setDataTransacao(LocalDateTime.now().minusDays(1));
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        transacao.setCliente(cliente);
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        transacao.setEmpresa(empresa);

        assertEquals("tipo", transacao.getTipo());
        assertEquals(100.0, transacao.getValor(), 0.001);
        assertNotNull(transacao.getDataTransacao());
        assertTrue(transacao.getDataTransacao().isBefore(LocalDateTime.now()));
        assertEquals(cliente, transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
    }

    // Criar uma transação com um 'valor' igual a zero
    @Test
    public void test_criar_transacao_com_valor_zero() {
        Transacao transacao = new Transacao();
        transacao.setTipo("tipo");
        transacao.setValor(0.0);
        transacao.setDataTransacao(LocalDateTime.now());
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        transacao.setCliente(cliente);
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        transacao.setEmpresa(empresa);

        assertEquals("tipo", transacao.getTipo());
        assertEquals(0.0, transacao.getValor(), 0.001);
        assertNotNull(transacao.getDataTransacao());
        assertEquals(cliente, transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
    }

    // Criar uma transação com um 'tipo' nulo
    @Test
    public void test_criar_transacao_com_tipo_nulo() {
        Transacao transacao = new Transacao();
        transacao.setTipo(null);
        transacao.setValor(100.0);
        transacao.setDataTransacao(LocalDateTime.now());
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        transacao.setCliente(cliente);
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        transacao.setEmpresa(empresa);

        assertNull(transacao.getTipo());
        assertEquals(100.0, transacao.getValor(), 0.001);
        assertNotNull(transacao.getDataTransacao());
        assertEquals(cliente, transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
    }

    // Criar uma transação com um 'cliente' nulo
    @Test
    public void test_criar_transacao_com_cliente_nulo() {
        Transacao transacao = new Transacao();
        transacao.setTipo("tipo");
        transacao.setValor(100.0);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setCliente(null);
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        transacao.setEmpresa(empresa);

        assertEquals("tipo", transacao.getTipo());
        assertEquals(100.0, transacao.getValor(), 0.001);
        assertNotNull(transacao.getDataTransacao());
        assertNull(transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
    }
}
