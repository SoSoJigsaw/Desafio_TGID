package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.dto.EmailDTO;
import tgid.kafka.producer.KafkaProducer;

@Service
public class NotificacaoClienteImpl implements NotificacaoCliente {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoCliente.class);

    @Autowired
    public KafkaProducer kafkaProducer;

    public NotificacaoClienteImpl(KafkaProducer kafkaProducerMock) {
        this.kafkaProducer = kafkaProducerMock;
    }

    public NotificacaoClienteImpl() {

    }

    @Override
    public void enviarNotificacaoKafka(String destinatario, String assunto, String corpo) {

        if (destinatario == null || destinatario.isEmpty()) {
            throw new IllegalArgumentException("Destinatario não pode ser nulo");
        }
        if (assunto == null || assunto.isEmpty()) {
            throw new IllegalArgumentException("Assunto não pode ser nulo");
        }
        if (corpo == null || corpo.isEmpty()) {
            throw new IllegalArgumentException("Corpo não pode ser nulo");
        }

        EmailDTO emailDTO = new EmailDTO(destinatario, assunto, corpo);

        try {
            kafkaProducer.enviarMensagemTransacao(emailDTO);
        } catch (JsonProcessingException e) {
            logger.info("Houve um erro ao solicitar o email: " + e.getMessage());
        }
    }

}

