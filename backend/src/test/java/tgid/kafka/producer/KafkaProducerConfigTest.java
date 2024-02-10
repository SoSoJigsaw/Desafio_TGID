package tgid.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerConfigTest {

    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    // O método producerFactory deve retornar um objeto DefaultKafkaProducerFactory com as propriedades de configuração corretas.
    @Test
    public void testSeProducerFactoryRetornaDefaultKafkaProducerFactoryComPropriedadesCorretas() {
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
    public void testSeTransacaoRequestTopicBuilderRetornaNewTopicComPropriedadesCorretas() {
        NewTopic newTopic = kafkaProducerConfig.transacaoRequestTopicBuilder();

        assertNotNull(newTopic);
        assertEquals(kafkaProducerConfig.transacaoResquestTopic, newTopic.name());
        assertEquals(1, newTopic.numPartitions());
        assertEquals(1, newTopic.replicationFactor());
    }

}
