package tgid.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@TestConfiguration
@Import(KafkaConsumerConfig.class)
public class KafkaConsumerConfigTests {

    // O método consumerFactory() deve retornar um objeto ConsumerFactory válido com as propriedades corretas definidas
    @Test
    public void test_consumerFactory_validObjectWithCorrectProperties() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        ConsumerFactory<String, String> factory = config.consumerFactory();

        assertNotNull(factory);
        assertEquals("host.docker.internal:9092", factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals("latest", factory.getConfigurationProperties().get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
        assertEquals("true", factory.getConfigurationProperties().get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
        assertEquals("email-notification-group", factory.getConfigurationProperties().get(ConsumerConfig.GROUP_ID_CONFIG));
        assertEquals(StringDeserializer.class.getName(), factory.getConfigurationProperties().get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(StringDeserializer.class.getName(), factory.getConfigurationProperties().get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    }

    // O método kafkaListenerContainerFactory() deve retornar um objeto ConcurrentKafkaListenerContainerFactory válido
    // com as propriedades corretas definidas
    @Test
    public void test_kafkaListenerContainerFactory_validObjectWithCorrectProperties() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        ConcurrentKafkaListenerContainerFactory<String, String> factory = config.kafkaListenerContainerFactory();

        assertNotNull(factory);
        assertEquals(config.consumerFactory(), factory.getConsumerFactory());
    }

    // A propriedade BOOTSTRAP_SERVERS_CONFIG deve ser definida para o endereço correto do servidor Kafka
    @Test
    public void test_BOOTSTRAP_SERVERS_CONFIG_correctAddress() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        ConsumerFactory<String, String> factory = config.consumerFactory();

        assertEquals("host.docker.internal:9092", factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    // Se BOOTSTRAP_SERVERS_CONFIG não estiver definido, consumerFactory() deve lançar uma exceção
    @Test
    public void test_consumerFactory_BOOTSTRAP_SERVERS_CONFIGNotSet() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email-notification-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try {
            config.consumerFactory();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("Missing required configuration \"" + ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + "\"", e.getMessage());
        }
    }

    // Se KEY_DESERIALIZER_CLASS_CONFIG não estiver definido, consumerFactory() deve lançar uma exceção
    @Test
    public void test_consumerFactory_KEY_DESERIALIZER_CLASS_CONFIGNotSet() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "host.docker.internal:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email-notification-group");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try {
            config.consumerFactory();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("Missing required configuration \"" + ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG + "\"", e.getMessage());
        }
    }

    // Se VALUE_DESERIALIZER_CLASS_CONFIG não estiver definido, consumerFactory() deve lançar uma exceção
    @Test
    public void test_consumerFactory_VALUE_DESERIALIZER_CLASS_CONFIGNotSet() {
        KafkaConsumerConfig config = new KafkaConsumerConfig();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "host.docker.internal:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email-notification-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try {
            config.consumerFactory();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("Missing required configuration \"" + ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG + "\"", e.getMessage());
        }
    }

}
