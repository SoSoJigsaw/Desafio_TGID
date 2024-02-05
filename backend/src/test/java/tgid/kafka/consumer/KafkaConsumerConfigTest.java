package tgid.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@Import(KafkaConsumerConfig.class)
public class KafkaConsumerConfigTest {

    // O método consumerFactory() deve retornar um objeto ConsumerFactory válido com as propriedades corretas definidas
    @Test
    public void test_consumerFactory_validObjectWithCorrectProperties() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        ConsumerFactory<String, String> factory = config.consumerFactory();

        assertNotNull(factory);
        assertEquals("localhost:9092", factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals("latest", factory.getConfigurationProperties().get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
        assertEquals("true", factory.getConfigurationProperties().get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
        assertEquals("email-notification-group", factory.getConfigurationProperties().get(ConsumerConfig.GROUP_ID_CONFIG));
        assertEquals(StringDeserializer.class.getName(), factory.getConfigurationProperties().get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(StringDeserializer.class.getName(), factory.getConfigurationProperties().get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    }

    // A propriedade BOOTSTRAP_SERVERS_CONFIG deve ser definida para o endereço correto do servidor Kafka
    @Test
    public void test_BOOTSTRAP_SERVERS_CONFIG_correctAddress() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        ConsumerFactory<String, String> factory = config.consumerFactory();

        assertEquals("localhost:9092", factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

}
