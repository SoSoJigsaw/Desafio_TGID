package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tgid.dto.CallbackDTO;
import tgid.kafka.producer.KafkaProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotificacaoEmpresaImplTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private NotificacaoEmpresaImpl notificacaoEmpresa;


    // Dado um URL e uma mensagem válidos, quando enviarCallbackKafka é chamado, um callbackDTO é
    // criado e enviado para kafkaProducer
    @Test
    public void testEnviarCallbackKafkaComUrlMensagemValidosEnviaCallbackDTOParaKafkaProducer() throws JsonProcessingException {
        // Arranjo
        String url = "https://exemplo.com";
        String mensagem = "Mensagem Teste";
        CallbackDTO expectedCallbackDTO = new CallbackDTO(url, mensagem);

        // Agir
        notificacaoEmpresa.enviarCallbackKafka(url, mensagem);

        // Afirmar
        verify(kafkaProducer).enviarMensagemTransacaoEmpresa(argThat(new ArgumentMatcher<CallbackDTO>() {
            @Override
            public boolean matches(CallbackDTO argument) {
                return argument.getUrl().equals(url) && argument.getMensagem().equals(mensagem);
            }
        }));

    }

    // Dados parâmetros válidos, quando formatCallbackSucesso é chamado, uma mensagem de sucesso é retornada
    @Test
    public void testFormatarCallbackSucessoRetornaMensagemDeSucesso() {
        // Arranjo
        String taxaNome = "Depósito";
        int valor = 100;
        String clienteNome = "Cliente Teste";
        String empresaNome = "Empresa Teste";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;

        String expectedMessage = "Transação recebida: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " +
                dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Saldo atual da empresa " + empresaNome + ": " +
                saldo.intValue() + " reais.";

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackSucesso(taxaNome, valor, clienteNome, empresaNome,
                dataTransacao, saldo);

        // Afirmar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dados parâmetros válidos, quando for chamado formatCallbackFalhaEmpresa, será retornada uma mensagem de
    // falha por falta de saldo da empresa
    @Test
    public void testFormatarCallbackFalhaEmpresaRetornaMensagemDeFalhaPorFaltaDeSaldoEmpresa() {
        // Arranjo
        String taxaNome = "Saque";
        int valor = 100;
        String clienteNome = "Cliente Teste";
        String empresaNome = "Empresa Teste";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;
        Double saldoVirtual = 100.0;

        String expectedMessage = "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " +
                dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Não pôde ser concluída por falta atual de saldo de sua empresa. \nSaldo atual da empresa " +
                empresaNome + ": " + saldo.intValue() + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtual.intValue();

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackFalhaEmpresa(taxaNome, valor, clienteNome,
                empresaNome, dataTransacao, saldo, saldoVirtual);

        // Afirmar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dados parâmetros válidos, quando formatCallbackFalhaCliente é chamado, é retornada uma mensagem de
    // falha por falta de saldo do cliente
    @Test
    public void testFormatarCallbackFalhaClienteRetornaMensagemDeFalhaPorFaltaDeSaldoCliente() {
        // Arranjo
        String taxaNome = "Depósito";
        int valor = 100;
        String clienteNome = "Cliente Teste";
        String empresaNome = "Empresa Teste";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;
        Double saldoVirtual = 100.0;

        String expectedMessage = "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " +
                dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")) + ".\n " +
                "Não pôde ser concluída por falta atual de saldo do cliente. \nSaldo atual da empresa " +
                empresaNome + ": " + saldo.intValue() + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtual.intValue();

        // Agir
        String actualMessage = notificacaoEmpresa.formatCallbackFalhaCliente(taxaNome, valor, clienteNome,
                empresaNome, dataTransacao, saldo, saldoVirtual);

        // Afirmar
        assertEquals(expectedMessage, actualMessage);
    }

    // Dada uma URL vazia, quando enviarCallbackKafka é chamado, então uma IllegalArgumentException é lançada
    @Test
    public void testEnviarCallbackKafkaComUrlVaziaLancaIllegalArgumentException() {
        // Arranjo
        String url = "";
        String mensagem = "Mensagem teste";

        // Agir e Afirmar
        assertThrows(IllegalArgumentException.class, () -> {
            notificacaoEmpresa.enviarCallbackKafka(url, mensagem);
        });
    }
}
