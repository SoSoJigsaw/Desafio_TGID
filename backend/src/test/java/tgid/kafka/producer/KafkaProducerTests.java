package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import tgid.dto.EmailDTO;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerTests {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerTests.class);

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    // O método serializa com sucesso o objeto de e-mail em uma string JSON
    @Test
    public void test_serialize_email_to_json() throws JsonProcessingException {
        // Arrange
        EmailDTO email = new EmailDTO("test@example.com", "Test Subject", "Test Body");
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapper.writeValueAsString(Mockito.any(EmailDTO.class))).thenReturn("SerializedEmail");

        // Act
        String json = objectMapper.writeValueAsString(email);

        // Assert
        assertNotNull(json);
        assertTrue(json.contains("test@example.com"));
        assertTrue(json.contains("Test Subject"));
        assertTrue(json.contains("Test Body"));
    }

    // KafkaTemplate envia com sucesso o objeto de e-mail serializado para o tópico especificado
    @Test
    public void test_send_serialized_email_to_topic() throws JsonProcessingException {
        // Arrange
        EmailDTO email = new EmailDTO("test@example.com", "Test Subject", "Test Body");

        // Act
        kafkaProducer.enviarMensagemTransacao(email);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq("transacao.request.topic"), anyString());
    }

    // Logger registra com sucesso a mensagem "Email enviado para processamento"
    @Test
    public void test_logger_logs_email_processing_message() throws JsonProcessingException {
        // Arrange
        EmailDTO email = new EmailDTO("test@example.com", "Test Subject", "Test Body");

        // Act
        kafkaProducer.enviarMensagemTransacao(email);

        // Assert
        verify(logger, times(1)).info("Email enviado para processamento");
    }

    // O objeto de e-mail é nulo, o método lança um NullPointerException
    @Test
    public void test_email_object_is_null() {
        // Arrange
        EmailDTO email = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducer.enviarMensagemTransacao(email));
    }

    // KafkaTemplate é nulo, o método lança um NullPointerException
    @Test
    public void test_kafka_template_is_null() {
        // Arrange
        kafkaProducer.setKafkaTemplate(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducer.enviarMensagemTransacao(new EmailDTO()));
    }

    // transacaoRequestTopic é nulo, o método lança um NullPointerException
    @Test
    public void test_transacao_request_topic_is_null() {
        // Arrange
        kafkaProducer.setTransacaoRequestTopic(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducer.enviarMensagemTransacao(new EmailDTO()));
    }

}
