package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tgid.dto.EmailDTO;
import tgid.kafka.producer.KafkaProducerImpl;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotificacaoClienteImplTest {

    // Deve enviar com sucesso um e-mail de notificação quando todos os parâmetros forem válidos
    @Test
    public void test_sendNotificationEmail_ValidParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "test@example.com";
        String assunto = "Test Subject";
        String corpo = "Test Body";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacaoCliente(any(EmailDTO.class));
    }

    // Deve lançar IllegalArgumentException quando o parâmetro 'destinatario' for nulo ou vazio
    @Test
    public void test_sendNotificationEmail_NullOrEmptyDestinatario() {
        // Arrange
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
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
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
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
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "a".repeat(255);
        String assunto = "b".repeat(255);
        String corpo = "c".repeat(1000);

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacaoCliente(any(EmailDTO.class));
    }

    // Deve enviar com sucesso um e-mail de notificação quando os parâmetros 'destinatario', 'assunto' e 'corpo'
    // tiverem o comprimento mínimo permitido
    @Test
    public void test_sendNotificationEmail_MinLengthParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "a";
        String assunto = "b";
        String corpo = "c";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacaoCliente(any(EmailDTO.class));
    }

    // Deve enviar com sucesso um e-mail de notificação quando os parâmetros 'destinatario', 'assunto' e 'corpo'
    // contiverem caracteres especiais
    @Test
    public void test_sendNotificationEmail_SpecialCharactersParameters() throws JsonProcessingException {
        // Arrange
        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);
        NotificacaoClienteImpl notificacaoCliente = new NotificacaoClienteImpl(kafkaProducerMock);

        String destinatario = "test@example.com";
        String assunto = "Test Subject #1";
        String corpo = "Test Body $%^";

        // Act
        notificacaoCliente.enviarNotificacaoKafka(destinatario, assunto, corpo);

        // Assert
        verify(kafkaProducerMock, times(1)).enviarMensagemTransacaoCliente(any(EmailDTO.class));
    }

}
