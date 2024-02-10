package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;
import tgid.dto.CallbackDTO;
import tgid.dto.EmailDTO;
import tgid.kafka.consumer.KafkaConsumerImpl;

@Slf4j
@Service
public class KafkaProducerImpl implements KafkaProducer {

    @Getter
    @Value("${transacao.request.topic}")
    private String transacaoResquestTopic;

    @Getter
    @Value("${callback.request.topic}")
    private String callbackRequestTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void enviarMensagemTransacaoCliente(EmailDTO email) throws JsonProcessingException {

        try {
            String conteudo = objectMapper.writeValueAsString(email);

            log.info("Email enviado para processamento");

            kafkaTemplate.send(transacaoResquestTopic, conteudo);

        } catch(JsonProcessingException e) {
            log.error("Erro ao converter o JSON em String: {}", e.getMessage());
        }
    }

    @Override
    public void enviarMensagemTransacaoEmpresa(CallbackDTO callback) throws JsonProcessingException {

        try {

            String conteudo = objectMapper.writeValueAsString(callback);

            log.info("Callback enviado para processamento");

            kafkaTemplate.send(callbackRequestTopic, conteudo);

        } catch (JsonProcessingException e) {
            log.error("Erro ao converter o Json em String: {}", e.getMessage());
        }
    }

}
