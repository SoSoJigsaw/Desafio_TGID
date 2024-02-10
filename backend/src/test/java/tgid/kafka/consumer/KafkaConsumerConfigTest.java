package tgid.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConsumerConfigTest {

    @Autowired
    KafkaConsumerConfig kafkaConsumerConfig;

    // O método consumerFactory() deve retornar um objeto ConsumerFactory válido com as propriedades corretas definidas
    @Test
    public void testSeConsumerFactoryRetornaUmObjetoValidoComAsPropriedadesCorretas() {
        ConsumerFactory<String, String> factory = kafkaConsumerConfig.consumerFactory();

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
    public void testSeBOOTSTRAP_SERVERS_CONFIG_EstaDefinidoCorretamente() {
        ConsumerFactory<String, String> factory = kafkaConsumerConfig.consumerFactory();

        assertEquals("localhost:9092", factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

}
