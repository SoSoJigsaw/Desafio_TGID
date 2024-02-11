package tgid.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import tgid.dto.CallbackDTO;
import tgid.dto.EmailDTO;
import tgid.exception.NotificacaoClienteException;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class KafkaConsumerImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KafkaConsumerImpl kafkaConsumer;

    // Processar com sucesso a mensagem de e-mail do produtor
    @Test
    public void testProcessarMensagemEmailDoProducer() throws JsonProcessingException {
        // Mockar ObjectMapper
        EmailDTO emailDTO = new EmailDTO("test@example.com", "Assunto Teste", "Corpo Teste");
        String json = "{\"destinatario\":\"test@example.com\",\"assunto\":\"Assunto Teste\",\"corpo\":\"Corpo Teste\"}";
        when(objectMapper.readValue(anyString(), eq(EmailDTO.class))).thenReturn(emailDTO);

        // Mockar JavaMailSender
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));

        // Invocar método em teste
        kafkaConsumer.processarMensagemTransacaoCliente(json);

        // Verificar se o email foi enviado com sucesso
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    // Envia o email com sucesso para o usuário
    @Test
    public void testEnviarEmailParaUsuario() throws Exception {

       when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));

        // Invocar método em teste
        kafkaConsumer.enviarEmailAoUsuario("test@example.com", "Assunto Teste", "Corpo Teste");

        // Verificar se o email foi enviado com sucesso
       verify(mailSender,times(1)).send(any(MimeMessage.class));
    }

    // Processa o callback com sucesso do produtor
    @Test
    public void testProcessarMensagemCallbackDoProducer() throws JsonProcessingException {

        CallbackDTO callbackDTO = new CallbackDTO("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b", "Mensagem teste");
        String json = "{\"url\":\"https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b\",\"mensagem\":\"Mensagem teste\"}";
        when(objectMapper.readValue(anyString(), eq(CallbackDTO.class))).thenReturn(callbackDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("Mensagem teste", headers);
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange("https://webhook.site/5897243b-cbcd-492c-843e-ca3830f9de3b",
                HttpMethod.POST, requestEntity, String.class)).thenReturn(response);

        // Invocar método em teste
        kafkaConsumer.processarMensagemTransacaoEmpresa(json);
    }

    // Erro ao enviar e-mail para o usuário
    @Test
    public void testErroAoEnviarEmailAoUsuario() throws Exception {
        // Mockar JavaMailSender
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doThrow(new MessagingException("Error sending email")).when(mailSender).send(any(MimeMessage.class));

        // Invocar método em teste
        assertThrows(NotificacaoClienteException.class, () -> kafkaConsumer.enviarEmailAoUsuario("test@example.com", "Test Subject", "Test Body"));
    }
}
