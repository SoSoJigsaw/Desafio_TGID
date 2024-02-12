package tgid.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${transacao.request.topic}")
    String transacaoResquestTopic;

    @Value("${callback.request.topic}")
    String callbackRequestTopic;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }

    @Bean
    public NewTopic transacaoRequestTopicBuilder() {
        return TopicBuilder
                .name(transacaoResquestTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic callbackRequestTopicBuilder() {
        return TopicBuilder
                .name(callbackRequestTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public void setValueSerializerClassConfig(Object o) {
    }

    public void setKeySerializerClassConfig(Object o) {
    }

    public void setBootstrapServersConfig(Object o) {
    }
}
