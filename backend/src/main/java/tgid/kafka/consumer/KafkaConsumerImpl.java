package tgid.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tgid.dto.CallbackDTO;
import tgid.dto.EmailDTO;
import tgid.exception.NotificacaoClienteException;
import tgid.exception.NotificacaoEmpresaException;

@Slf4j
@Service
public class KafkaConsumerImpl implements KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate;

    public KafkaConsumerImpl(ObjectMapper objectMapper, JavaMailSender mailSender, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }

    @Override
    @KafkaListener(
            topics = "transacao.request.topic",
            containerFactory = "kafkaListenerContainerFactory",
            topicPartitions = @TopicPartition(topic = "transacao.request.topic", partitions = {"0"})
    )
    public void processarMensagemTransacaoCliente(String conteudo) {

        log.info("Processando a mensagem do produtor...");

        try {
            // Converte a mensagem JSON de volta para um objeto EmailDTO
            EmailDTO emailDTO = objectMapper.readValue(conteudo, EmailDTO.class);

            // Extrai os atributos do objeto EmailDTO
            String destinatario = emailDTO.getDestinatario();
            String assunto = emailDTO.getAssunto();
            String corpo = emailDTO.getCorpo();

            // Lógica de enviar o email
            enviarEmailAoUsuario(destinatario, assunto, corpo);

        } catch (JsonProcessingException e) {
            log.error("Erro no processo de leitura do JSON para convertê-lo ao formato de email: {}", e.getMessage());
        }
    }

    @Override
    public void enviarEmailAoUsuario(String destinatario, String assunto, String corpo) {

        // Cria uma mensagem de email
        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper mensagem = new MimeMessageHelper(email);

        try {
            mensagem.setTo(destinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);

            mailSender.send(email);

            log.info("E-mail enviado com sucesso para: {}", destinatario);

        } catch (Exception e) {
            log.error("Envio de email de notificação ao cliente falhou: " + e.getMessage());
            throw new NotificacaoClienteException(e);
        }
    }

    @Override
    @KafkaListener(
            topics = "callback.request.topic",
            containerFactory = "kafkaListenerContainerFactory",
            topicPartitions = @TopicPartition(topic = "callback.request.topic", partitions = {"0"})
    )
    public void processarMensagemTransacaoEmpresa(String conteudo) {

        log.info("Processando o callback do produtor...");

        try {
            // Converte a mensagem JSON de volta para um objeto CallbackDTO
            CallbackDTO callback = objectMapper.readValue(conteudo, CallbackDTO.class);

            // Extrai os atributos do objeto CallbackDTO
            String url = callback.getUrl();
            String mensagem = callback.getMensagem();

            // Lógica de enviar o Callback
            enviarCallbackEmpresa(url, mensagem);

        } catch (JsonProcessingException e) {
            log.error("Erro no processo de leitura do JSON para convertê-lo ao formato de email: {}", e.getMessage());
        }
    }

    @Override
    public void enviarCallbackEmpresa(String url, String mensagem) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);

            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Callback enviado com sucesso. Resposta: {}", response.getBody());
            } else {
                log.error("Callback para a empresa falhou. StatusCode: {}", response.getStatusCode());
                throw new NotificacaoEmpresaException(response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Callback para a empresa falhou: " + e.getMessage());
            throw new NotificacaoEmpresaException(e);
        }
    }
}
