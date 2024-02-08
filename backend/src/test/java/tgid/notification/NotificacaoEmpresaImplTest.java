package tgid.notification;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class NotificacaoEmpresaImplTest {

    // Dados parâmetros válidos, quando formatCallbackSucesso é chamado, então uma mensagem de sucesso é retornada
    @Test
    public void test_formatCallbackSucesso_validParameters_successMessageReturned() {
        // Preparar
        String taxaNome = "Taxa";
        int valor = 100;
        String clienteNome = "Cliente";
        String empresaNome = "Empresa";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;

        String expectedMessage = "Transação recebida: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Saldo atual da empresa " + empresaNome + ": " +
                saldo.intValue() + " reais.";

        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(null);

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackSucesso(taxaNome, valor, clienteNome, empresaNome, dataTransacao, saldo);

        // Assegurar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dados parâmetros válidos, quando formatCallbackFalhaEmpresa é chamado, então uma mensagem de falha devido à falta de saldo da empresa é retornada
    @Test
    public void test_formatCallbackFalhaEmpresa_validParameters_failureMessageDueToLackOfCompanyBalanceReturned() {
        // Preparar
        String taxaNome = "Taxa";
        int valor = 100;
        String clienteNome = "Cliente";
        String empresaNome = "Empresa";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;
        Double saldoVirtual = 200.0;

        String expectedMessage = "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Não pôde ser concluída por falta atual de saldo de sua empresa. \nSaldo atual da empresa " +
                empresaNome + ": " + saldo.intValue() + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtual.intValue();

        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(null);

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackFalhaEmpresa(taxaNome, valor, clienteNome, empresaNome, dataTransacao, saldo, saldoVirtual);

        // Assegurar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dados parâmetros válidos, quando formatCallbackFalhaCliente é chamado, então uma mensagem de falha devido à falta de saldo do cliente é retornada
    @Test
    public void test_formatCallbackFalhaCliente_validParameters_failureMessageDueToLackOfClientBalanceReturned() {
        // Preparar
        String taxaNome = "Taxa";
        int valor = 100;
        String clienteNome = "Cliente";
        String empresaNome = "Empresa";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;
        Double saldoVirtual = 200.0;

        String expectedMessage = "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Não pôde ser concluída por falta atual de saldo do cliente. \nSaldo atual da empresa " +
                empresaNome + ": " + saldo.intValue() + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtual.intValue();

        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(null);

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackFalhaCliente(taxaNome, valor, clienteNome, empresaNome, dataTransacao, saldo, saldoVirtual);

        // Assegurar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dada uma url nula, quando enviarCallbackKafka é chamado, então é lançada uma IllegalArgumentException
    @Test
    public void test_enviarCallbackKafka_nullUrl_illegalArgumentExceptionThrown() {
        // Preparar
        String url = null;
        String mensagem = "Mensagem de teste";

        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(null);

        // Agir & Assegurar
        assertThrows(IllegalArgumentException.class, () -> {
            notificacaoEmpresa.enviarCallbackKafka(url, mensagem);
        });
    }

}
