package tgid.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessagingException;
import tgid.dto.EmailDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

@TestConfiguration
@Import(KafkaConsumer.class)
public class KafkaConsumerTests {

    // O método deve converter corretamente a mensagem JSON de volta para um objeto EmailDTO.
    @Test
    public void test_convertJsonToEmailDTO() throws JsonProcessingException {
        // Arrange
        String conteudo = "{\"destinatario\":\"test@example.com\",\"assunto\":\"Test Subject\",\"corpo\":\"Test Body\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        EmailDTO emailDTO = objectMapper.readValue(conteudo, EmailDTO.class);

        // Assert
        assertEquals("test@example.com", emailDTO.getDestinatario());
        assertEquals("Test Subject", emailDTO.getAssunto());
        assertEquals("Test Body", emailDTO.getCorpo());
    }

    // O método deve extrair o destinatário, assunto e corpo do objeto EmailDTO.
    @Test
    public void test_extractEmailAttributes() throws JsonProcessingException {
        // Arrange
        String conteudo = "{\"destinatario\":\"test@example.com\",\"assunto\":\"Test Subject\",\"corpo\":\"Test Body\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        EmailDTO emailDTO = objectMapper.readValue(conteudo, EmailDTO.class);

        // Act
        String destinatario = emailDTO.getDestinatario();
        String assunto = emailDTO.getAssunto();
        String corpo = emailDTO.getCorpo();

        // Assert
        assertEquals("test@example.com", destinatario);
        assertEquals("Test Subject", assunto);
        assertEquals("Test Body", corpo);
    }

    // O método deve enviar com sucesso um email para o destinatário usando as informações extraídas.
    @Test
    public void test_sendEmailToRecipient() throws JsonProcessingException, MessagingException {
        // Arrange
        String conteudo = "{\"destinatario\":\"test@example.com\",\"assunto\":\"Test Subject\",\"corpo\":\"Test Body\"}";

        KafkaTemplate kafkaTemplate = mock(KafkaTemplate.class);

        // Act
        KafkaConsumer kafkaConsumer = mock(KafkaConsumer.class);
        kafkaConsumer.processarMensagemTransacao(conteudo);

        // Assert
        // Verificar se o email foi enviado com sucesso (assert com base em logs ou verificação da caixa de entrada de email)
    }

    // A mensagem JSON está vazia ou nula, e o método deve lidar com isso adequadamente.
    @Test
    public void test_handleEmptyOrNullJsonMessage() throws JsonProcessingException {
        // Arrange
        String conteudo = null;

        // Act and Assert
        KafkaTemplate kafkaTemplate = mock(KafkaTemplate.class);
        KafkaConsumer kafkaConsumer = mock(KafkaConsumer.class);
        assertThrows(JsonProcessingException.class, () -> {
            kafkaConsumer.processarMensagemTransacao(conteudo);
        });
    }

    // A mensagem JSON não está no formato esperado, e o método deve lançar uma JsonProcessingException.
    @Test
    public void test_handleInvalidJsonFormat() throws JsonProcessingException {
        // Arrange
        String conteudo = "{\"invalidField\":\"test@example.com\",\"assunto\":\"Test Subject\",\"corpo\":\"Test Body\"}";

        // Act and Assert
        KafkaTemplate kafkaTemplate = mock(KafkaTemplate.class);
        KafkaConsumer kafkaConsumer = mock(KafkaConsumer.class);
        assertThrows(JsonProcessingException.class, () -> {
            kafkaConsumer.processarMensagemTransacao(conteudo);
        });
    }

    // O endereço de email do destinatário é inválido, e o método deve lidar com isso adequadamente.
    @Test
    public void test_handleInvalidRecipientEmailAddress() throws JsonProcessingException {
        // Arrange
        String conteudo = "{\"destinatario\":\"invalid_email\",\"assunto\":\"Test Subject\",\"corpo\":\"Test Body\"}";

        // Act and Assert
        KafkaTemplate kafkaTemplate = mock(KafkaTemplate.class);
        KafkaConsumer kafkaConsumer = mock(KafkaConsumer.class);
        assertThrows(MessagingException.class, () -> {
            kafkaConsumer.processarMensagemTransacao(conteudo);
        });
    }

}
