package tgid.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
@Import(KafkaProducerConfig.class)
public class KafkaProducerConfigTest {

    // O método producerFactory deve retornar um objeto DefaultKafkaProducerFactory com as propriedades de configuração corretas.
    @Test
    public void test_producerFactory_returnsDefaultKafkaProducerFactoryWithCorrectConfigurationProperties() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        ProducerFactory<String, String> producerFactory = kafkaProducerConfig.producerFactory();

        assertNotNull(producerFactory);
        assertTrue(producerFactory instanceof DefaultKafkaProducerFactory);

        Map<String, Object> configProps = ((DefaultKafkaProducerFactory<String, String>) producerFactory).getConfigurationProperties();
        assertEquals("localhost:9092", ((Map<?, ?>) configProps).get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals("org.apache.kafka.common.serialization.StringSerializer", configProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals("org.apache.kafka.common.serialization.StringSerializer", configProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    // O método transacaoRequestTopicBuilder deve retornar um objeto NewTopic com o nome, partições e réplicas corretos.
    @Test
    public void test_transacaoRequestTopicBuilder_returnsNewTopicWithCorrectProperties() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        NewTopic newTopic = kafkaProducerConfig.transacaoRequestTopicBuilder();

        assertNotNull(newTopic);
        assertEquals(kafkaProducerConfig.transacaoResquestTopic, newTopic.name());
        assertEquals(1, newTopic.numPartitions());
        assertEquals(1, newTopic.replicationFactor());
    }

    // O valor de transacaoResquestTopic é nulo ou vazio.
    @Test
    public void test_transacaoResquestTopic_isNullOrEmpty() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        String transacaoResquestTopic = kafkaProducerConfig.transacaoResquestTopic;

        assertTrue(transacaoResquestTopic == null || transacaoResquestTopic.isEmpty());
    }

    // O valor de callbackRequestTopic é nulo ou vazio.
    @Test
    public void test_callbackRequestTopic_isNullOrEmpty() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        String callbackRequestTopic = kafkaProducerConfig.callbackRequestTopic;

        assertTrue(callbackRequestTopic == null || callbackRequestTopic.isEmpty());
    }

}
