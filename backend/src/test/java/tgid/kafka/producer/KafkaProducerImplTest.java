package tgid.kafka.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.EmailDTO;


import static org.junit.Assert.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerImplTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImplTest.class);

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerImpl kafkaProducerImpl;


    // O objeto de e-mail é nulo, o método lança um NullPointerException
    @Test
    public void test_email_object_is_null() {
        // Arrange
        EmailDTO email = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducerImpl.enviarMensagemTransacaoCliente(email));
    }

    // KafkaTemplate é nulo, o método lança um NullPointerException
    @Test
    public void test_kafka_template_is_null() {
        // Arrange
        kafkaProducerImpl.setKafkaTemplate(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducerImpl.enviarMensagemTransacaoCliente(new EmailDTO()));
    }

    // transacaoRequestTopic é nulo, o método lança um NullPointerException
    @Test
    public void test_transacao_request_topic_is_null() {
        // Arrange
        kafkaProducerImpl.setTransacaoRequestTopic(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> kafkaProducerImpl.enviarMensagemTransacaoCliente(new EmailDTO()));
    }

}
