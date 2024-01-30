package tgid.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.service.TransacaoService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTests {

    // can create a new instance of TransacaoController with a TransacaoService dependency
    @Test
    public void test_create_instance() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        assertNotNull(transacaoController);
    }

    // can perform a deposit transaction with deposito() method
    @Test
    public void test_perform_deposit_transaction() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = 1L;
        Long clienteId = 2L;
        double valor = 100.0;
        transacaoController.deposito(empresaId, clienteId, valor);
        verify(transacaoService).realizarDeposito(empresaId, clienteId, valor);
    }

    // can perform a withdrawal transaction with saque() method
    @Test
    public void test_perform_withdrawal_transaction() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = 1L;
        Long clienteId = 2L;
        double valor = 100.0;
        transacaoController.saque(empresaId, clienteId, valor);
        verify(transacaoService).realizarSaque(empresaId, clienteId, valor);
    }

    // deposito() method throws an exception if empresaId or clienteId is null
    @Test
    public void test_deposito_throws_exception_if_ids_are_null() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = null;
        Long clienteId = null;
        double valor = 100.0;
        assertThrows(Exception.class, () -> transacaoController.deposito(empresaId, clienteId, valor));
    }

    // deposito() method throws an exception if valor is negative
    @Test
    public void test_deposito_throws_exception_if_valor_is_negative() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = 1L;
        Long clienteId = 2L;
        double valor = -100.0;
        assertThrows(Exception.class, () -> transacaoController.deposito(empresaId, clienteId, valor));
    }

    // saque() method throws an exception if empresaId or clienteId is null
    @Test
    public void test_saque_throws_exception_if_ids_are_null() {
        TransacaoService transacaoService = mock(TransacaoService.class);
        TransacaoController transacaoController = new TransacaoController(transacaoService);
        Long empresaId = null;
        Long clienteId = null;
        double valor = 100.0;
        assertThrows(Exception.class, () -> transacaoController.saque(empresaId, clienteId, valor));
    }

}
