package tgid.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tgid.exception.KafkaException;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificacaoCliente {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoCliente.class);

    private static final String topicoEmail = "Transação concluída";

    public void enviarNotificacaoPorEmail(String destinatario, String assunto, String corpo) {

        String mensagemEmail = "{"
                + "\"destinatario\": \"" + destinatario + "\","
                + "\"assunto\": \"" + assunto + "\","
                + "\"corpo\": \"" + corpo + "\""
                + "}";

        try {

            // Enviando a mensagem para o Kafka
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicoEmail, mensagemEmail);

            // Adicionando uma callback para lidar com o resultado do envio
            future.whenComplete((result, ex) -> {

                if (ex != null) {

                    logger.error("Erro ao enviar mensagem para o Kafka: {}", ex.getMessage(), ex);
                    throw new KafkaException(ex);

                } else {

                    logger.info("Mensagem enviada com sucesso: {}", result.getProducerRecord().value());

                }
            });

        } catch (Exception e) {

            logger.error("Erro ao enviar mensagem para o Kafka: {}", e.getMessage(), e);

        }

    }

}
