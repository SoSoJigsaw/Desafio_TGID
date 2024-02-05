package tgid.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@Import(KafkaProducerConfig.class)
public class KafkaProducerConfigTest {

    // O método 'producerFactory' deve retornar uma instância não nula de 'DefaultKafkaProducerFactory'.
    @Test
    public void test_producerFactory_returnsNonNullInstance() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        ProducerFactory<String, String> producerFactory = kafkaProducerConfig.producerFactory();
        assertNotNull(producerFactory);
    }

    // O método 'kafkaTemplate' deve retornar uma instância não nula de 'KafkaTemplate'.
    @Test
    public void test_kafkaTemplate_returnsNonNullInstance() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        KafkaTemplate<String, String> kafkaTemplate = kafkaProducerConfig.kafkaTemplate();
        assertNotNull(kafkaTemplate);
    }

    // O método 'transacaoRequestTopicBuilder' deve retornar uma instância não nula de 'NewTopic'.
    @Test
    public void test_transacaoRequestTopicBuilder_returnsNonNullInstance() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        NewTopic newTopic = kafkaProducerConfig.transacaoRequestTopicBuilder();
        assertNotNull(newTopic);
    }

}
