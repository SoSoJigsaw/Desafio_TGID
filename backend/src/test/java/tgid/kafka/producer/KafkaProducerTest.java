package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.EmailDTO;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;


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
