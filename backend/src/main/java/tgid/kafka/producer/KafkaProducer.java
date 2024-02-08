package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import tgid.dto.CallbackDTO;
import tgid.dto.EmailDTO;

public interface KafkaProducer {

    void enviarMensagemTransacaoCliente(EmailDTO email) throws JsonProcessingException;

    void enviarMensagemTransacaoEmpresa(CallbackDTO callback) throws JsonProcessingException;

}
