package tgid.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@TestConfiguration
@Import(KafkaProducerConfig.class)
public class KafkaProducerConfigTests {

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

    // O método 'producerFactory' deve lançar uma exceção se a configuração dos servidores de inicialização for nula ou vazia.
    @Test
    public void test_producerFactory_throwsExceptionIfBootstrapServersConfigIsNull() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        kafkaProducerConfig.setBootstrapServersConfig(null);
        assertThrows(IllegalArgumentException.class, kafkaProducerConfig::producerFactory);
    }

    // O método 'producerFactory' deve lançar uma exceção se a configuração da classe serializadora de chave for nula ou vazia.
    @Test
    public void test_producerFactory_throwsExceptionIfKeySerializerClassConfigIsNull() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        kafkaProducerConfig.setKeySerializerClassConfig(null);
        assertThrows(IllegalArgumentException.class, kafkaProducerConfig::producerFactory);
    }

    // O método 'producerFactory' deve lançar uma exceção se a configuração da classe serializadora de valor for nula ou vazia.
    @Test
    public void test_producerFactory_throwsExceptionIfValueSerializerClassConfigIsNull() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        kafkaProducerConfig.setValueSerializerClassConfig(null);
        assertThrows(IllegalArgumentException.class, kafkaProducerConfig::producerFactory);
    }
}
