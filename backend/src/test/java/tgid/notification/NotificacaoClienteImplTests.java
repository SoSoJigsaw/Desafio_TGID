package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import tgid.dto.EmailDTO;
import tgid.kafka.producer.KafkaProducer;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@TestConfiguration
@Import(NotificacaoClienteImpl.class)
public class NotificacaoClienteImplTests {

    // Deve enviar com sucesso um e-mail de notificação quando todos os parâmetros forem válidos
    @Test
    public void test_sendNotificationEmail_ValidParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "test@example.com";
        String assunto = "Test Subject";
        String corpo = "Test Body";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacao(any(EmailDTO.class));
    }

    // Deve lançar IllegalArgumentException quando o parâmetro 'destinatario' for nulo ou vazio
    @Test
    public void test_sendNotificationEmail_NullOrEmptyDestinatario() {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = null;
        String assunto = "Test Subject";
        String corpo = "Test Body";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);
        });
    }

    // Deve lançar IllegalArgumentException quando o parâmetro 'assunto' for nulo ou vazio
    @Test
    public void test_sendNotificationEmail_NullOrEmptyAssunto() {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "test@example.com";
        String assunto = null;
        String corpo = "Test Body";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);
        });
    }

    // Deve enviar com sucesso um e-mail de notificação quando os parâmetros 'destinatario', 'assunto' e 'corpo'
    // tiverem o comprimento máximo permitido
    @Test
    public void test_sendNotificationEmail_MaxLengthParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "a".repeat(255);
        String assunto = "b".repeat(255);
        String corpo = "c".repeat(1000);

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacao(any(EmailDTO.class));
    }

    // Deve enviar com sucesso um e-mail de notificação quando os parâmetros 'destinatario', 'assunto' e 'corpo'
    // tiverem o comprimento mínimo permitido
    @Test
    public void test_sendNotificationEmail_MinLengthParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "a";
        String assunto = "b";
        String corpo = "c";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacao(any(EmailDTO.class));
    }

    // Deve enviar com sucesso um e-mail de notificação quando os parâmetros 'destinatario', 'assunto' e 'corpo'
    // contiverem caracteres especiais
    @Test
    public void test_sendNotificationEmail_SpecialCharactersParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducer kafkaProducerMock = mock(KafkaProducer.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "test@example.com";
        String assunto = "Test Subject #1";
        String corpo = "Test Body $%^";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacao(any(EmailDTO.class));
    }

}
