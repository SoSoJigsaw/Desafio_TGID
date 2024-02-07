package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;
import tgid.dto.EmailDTO;
import tgid.kafka.consumer.KafkaConsumer;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Value("${transacao.request.topic}")
    private String transacaoResquestTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, String transacaoRequestTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.transacaoResquestTopic = transacaoRequestTopic;
    }

    public KafkaProducer() {

    }

    public void enviarMensagemTransacao(EmailDTO email) throws JsonProcessingException {

        try {
            String conteudo = objectMapper.writeValueAsString(email);

            logger.info("Email enviado para processamento");

            kafkaTemplate.send(transacaoResquestTopic, conteudo);

        } catch(JsonProcessingException e) {
            logger.info("Erro ao converter o JSON em String: {}", e.getMessage());
        }
    }

    public void setKafkaTemplate(Object o) {
    }

    public void setTransacaoRequestTopic(Object o) {
    }
}
