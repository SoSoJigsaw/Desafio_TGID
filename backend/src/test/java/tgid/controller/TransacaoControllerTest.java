package tgid.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.service.TransacaoService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

    // Pode criar uma nova instância de TransacaoController com uma dependência TransacaoService
    @Test
    public void test_criar_instancia() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        assertNotNull(transacaoController);
    }

    // Pode realizar uma transação de depósito com o método deposito()
    @Test
    public void test_realizar_transacao_de_deposito() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = 1L;
        Long clienteId = 2L;
        double valor = 100.0;
        transacaoController.deposito(empresaId, clienteId, valor);
        verify(transacaoService).realizarDeposito(empresaId, clienteId, valor);
    }

    // Pode realizar uma transação de saque com o método saque()
    @Test
    public void test_realizar_transacao_de_saque() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = 1L;
        Long clienteId = 2L;
        double valor = 100.0;
        transacaoController.saque(empresaId, clienteId, valor);
        verify(transacaoService).realizarSaque(empresaId, clienteId, valor);
    }

}
