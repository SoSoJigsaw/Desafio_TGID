package tgid.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tgid.dto.EmailDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Import(KafkaConsumerImpl.class)
public class KafkaConsumerImplTest {

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
}
