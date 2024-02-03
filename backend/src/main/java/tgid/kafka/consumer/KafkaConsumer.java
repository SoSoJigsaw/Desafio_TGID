package tgid.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tgid.dto.EmailDTO;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JavaMailSender mailSender;

    @KafkaListener(
            topics = "transacao.request.topic",
            containerFactory = "kafkaListenerContainerFactory",
            topicPartitions = @TopicPartition(topic = "transacao.request.topic", partitions = {"0"})
    )
    public void processarMensagemTransacao(String conteudo) throws JsonProcessingException {

        logger.info("Processando a mensagem do produtor...");

        // Converte a mensagem JSON de volta para um objeto EmailDTO
        EmailDTO emailDTO = objectMapper.readValue(conteudo, EmailDTO.class);

        // Extrai os atributos do objeto EmailDTO
        String destinatario = emailDTO.getDestinatario();
        String assunto = emailDTO.getAssunto();
        String corpo = emailDTO.getCorpo();

        // Lógica de enviar o email
        enviarEmailAoUsuario(destinatario, assunto, corpo);
    }

    private void enviarEmailAoUsuario(String destinatario, String assunto, String corpo) {

        // Cria uma mensagem de email
        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper mensagem = new MimeMessageHelper(email);

        try {
            mensagem.setTo(destinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);

            mailSender.send(email);

            logger.info("E-mail enviado com sucesso para: {}", destinatario);
        } catch (MessagingException e) {
            logger.info("Erro ao enviar e-mail: {}", e.getMessage());
        }
    }

}
