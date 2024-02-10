package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.EmailDTO;
import tgid.kafka.producer.KafkaProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificacaoClienteImplTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    NotificacaoClienteImpl notificacaoCliente;

    // deve enviar com êxito um e-mail de notificação para um destinatário válido com assunto
    // e corpo não vazios
    @Test
    public void testEnviarNotificacaoEmailVálidoComAssuntoCorpoNaoNulos() throws JsonProcessingException {
        // Arranjo
        String destinatario = "test@example.com";
        String assunto = "Assunto Teste";
        String corpo = "Corpo Teste";

        // Agir
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Afirmar
        verify(kafkaProducer, times(1)).enviarMensagemTransacaoCliente(any(EmailDTO.class));
    }

    // deve formatar corretamente o assunto e o corpo de um e-mail de notificação de operação
    // concluída com sucesso
    @Test
    public void testFormatarCorretamenteAssuntoCorpoDoEmailNotificacao() {
        // Arranjo
        String tipoTransacao = "Depósito";
        int valorTransacao = 100;
        String nomeEmpresa = "Empresa Teste";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 500.0;

        // Agir
        String assunto = notificacaoCliente.formatAssuntoOperacaoRealizada(tipoTransacao, valorTransacao);
        String corpo = notificacaoCliente.formatCorpoOperacaoRealizada(tipoTransacao, valorTransacao, nomeEmpresa,
                dataTransacao, saldo);

        // Afirmar
        assertEquals("Depósito de 100 reais concluída", assunto);
        assertEquals("Seu Depósito no valor de 100 reais na empresa Empresa Teste foi concluída com sucesso. " +
                "\n Seu saldo agora é de 500 reais." + "\n Data/Hora da operação: " +
                dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")), corpo);
    }

    // deve formatar corretamente o assunto e o corpo de um e-mail de notificação de operação negada para uma
    // empresa com saldo insuficiente
    @Test
    public void testFormatarCorretamenteAssuntoCorpoDeNotificacaoNegadaParaUmaEmpresaComSaldoInsuficiente() {
        // Arranjo
        String tipoTransacao = "Saque";
        int valorTransacao = 200;
        String nomeEmpresa = "Empresa Teste";
        LocalDateTime dataTransacao = LocalDateTime.now();
        Double saldo = 100.0;

        // Agir
        String assunto = notificacaoCliente.formatAssuntoOperacaoNegada(tipoTransacao, valorTransacao);
        String corpo = notificacaoCliente.formatCorpoOperacaoNegadaEmpresa(tipoTransacao, valorTransacao, nomeEmpresa,
                dataTransacao, saldo);

        // Afirmar
        assertEquals("Operação de Saque negada", assunto);
        assertEquals("Tentativa de Saque no valor de 200 reais na empresa Empresa Teste não " +
                        "pôde ser concluída. " + "A empresa não possui saldo suficiente para a transação. " +
                        "\n Seu saldo continua sendo de 100 reais." + "\n Data/Hora da operação: " +
                        dataTransacao.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")), corpo);
    }

    // deve lançar uma IllegalArgumentException se o destinatário for nulo ou vazio ao tentar enviar
    // um email de notificação
    @Test
    public void testLancarIllegalArgumentExceptionSeDestinatarioForNuloOuVazio() {
        // Arranjo
        String destinatario = null;
        String assunto = "Assunto Teste";
        String corpo = "Corpo Teste";

        // Agir e Afirmar
        String destinatarioNulo = destinatario;
        assertThrows(IllegalArgumentException.class, () -> notificacaoCliente.enviarNotificacaoKafka(destinatarioNulo,
                assunto, corpo));

        String destinatarioVazio = "";
        assertThrows(IllegalArgumentException.class, () -> notificacaoCliente.enviarNotificacaoKafka(destinatarioVazio,
                assunto, corpo));
    }

    // deve lançar uma IllegalArgumentException se o assunto for nulo ou vazio ao tentar enviar um
    // email de notificação
    @Test
    public void testLancarIllegalArgumentExceptionSeAssuntoForNuloOuVazio() {
        // Arranjo
        String destinatario = "test@example.com";
        String assunto = null;
        String corpo = "Corpo Teste";

        // Agir e Afirmar
        String assuntoNulo = assunto;
        assertThrows(IllegalArgumentException.class, () -> notificacaoCliente.enviarNotificacaoKafka(destinatario,
                assuntoNulo, corpo));

        String assuntoVazio = "";
        assertThrows(IllegalArgumentException.class, () -> notificacaoCliente.enviarNotificacaoKafka(destinatario,
                assuntoVazio, corpo));
    }

}
