package tgid.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.TransacaoDTO;
import tgid.exception.TransacaoInvalidaException;
import tgid.exception.TransacaoNegativaException;
import tgid.exception.TransacaoZeradaException;
import tgid.service.TransacaoService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransacaoControllerTest {

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController transacaoController;

    // TransacaoController pode listar todas as transações e retornar uma resposta bem-sucedida
    @Test
    public void testListarTodasTransacoesComRespostaBemSucedida() {

        List<TransacaoDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(new TransacaoDTO(1L, "Depósito", 100.0, "2021-01-01", "Cliente 1", "Empresa 1"));
        expectedResponse.add(new TransacaoDTO(2L, "Saque", 50.0, "2021-01-02", "Cliente 2", "Empresa 2"));

        when(transacaoService.listarTodasTransacoes()).thenReturn(expectedResponse);

        List<TransacaoDTO> actualResponse = transacaoController.listarTodasTransacoes();

        assertEquals(expectedResponse, actualResponse);
    }

    // TransacaoController pode tratar e retornar uma resposta de erro quando o valor do depósito for negativo
    @Test
    public void testDepositoComValorNegativoRetornaRespostaDeErro() {

        Long empresaId = 1L;
        Long clienteId = 1L;
        double valor = -100.0;

        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().body("Erro no processamento da transação de depósito: O valor do depósito não pode ser negativo");

        when(transacaoService.realizarDeposito(empresaId, clienteId, valor)).thenThrow(new TransacaoInvalidaException("depósito", new TransacaoNegativaException("O valor do depósito não pode ser negativo")));

        try {
            transacaoController.deposito(empresaId, clienteId, valor);
            fail("Expected TransacaoInvalidaException to be thrown");
        } catch (TransacaoInvalidaException e) {
            assertEquals(expectedResponse.getBody(), e.getMessage());
        }
    }

    // TransacaoController pode tratar e retornar uma resposta de erro quando o valor de retirada for negativo
    @Test
    public void testSaqueComValorNegativoRetornaRespostaDeErro() {

        Long empresaId = 1L;
        Long clienteId = 1L;
        double valor = -50.0;

        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().body("Erro no processamento da transação de saque: O valor do saque não pode ser negativo");

        when(transacaoService.realizarSaque(empresaId, clienteId, valor)).thenThrow(new TransacaoInvalidaException("saque", new TransacaoNegativaException("O valor do saque não pode ser negativo")));

        try {
            transacaoController.saque(empresaId, clienteId, valor);
            fail("Expected TransacaoInvalidaException to be thrown");
        } catch (TransacaoInvalidaException e) {
            assertEquals(expectedResponse.getBody(), e.getMessage());
        }
    }

    // TransacaoController pode tratar e retornar uma resposta de erro quando o valor do depósito for zero
    @Test
    public void testDepositoComValorZeradoRetornaRespostaDeErro() {

        Long empresaId = 1L;
        Long clienteId = 1L;
        double valor = 0.0;

        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().body("Erro no processamento da transação de depósito: O valor do depósito não pode ser zero");

        when(transacaoService.realizarDeposito(empresaId, clienteId, valor)).thenThrow(new TransacaoInvalidaException("depósito", new TransacaoZeradaException("O valor do depósito não pode ser zero")));

        try {
            transacaoController.deposito(empresaId, clienteId, valor);
            fail("Expected TransacaoInvalidaException to be thrown");
        } catch (TransacaoInvalidaException e) {
            assertEquals(expectedResponse.getBody(), e.getMessage());
        }
    }

    // TransacaoController pode tratar e retornar uma resposta de erro quando o valor do saque for zero
    @Test
    public void testSaqueComValorZeradoRetornaRespostaDeErro() {

        Long empresaId = 1L;
        Long clienteId = 1L;
        double valor = 0.0;

        ResponseEntity<?> expectedResponse = ResponseEntity.badRequest().body("Erro no processamento da transação de saque: O valor do saque não pode ser zero");

        when(transacaoService.realizarDeposito(empresaId, clienteId, valor)).thenThrow(new TransacaoInvalidaException("saque", new TransacaoZeradaException("O valor do saque não pode ser zero")));

        try {
            transacaoController.saque(empresaId, clienteId, valor);
            fail("Expected TransacaoInvalidaException to be thrown");
        } catch (TransacaoInvalidaException e) {
            assertEquals(expectedResponse.getBody(), e.getMessage());
        }
    }

}
