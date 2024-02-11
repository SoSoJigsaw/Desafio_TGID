package tgid.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tgid.dto.CallbackDTO;
import tgid.dto.EmailDTO;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class KafkaProducerImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducerImpl kafkaProducer;


    // Enviar mensagem de e-mail com sucesso para o tópico de solicitação de transação
    @Test
    public void testEnviaMensagemEmailParaTransactionRequestTopic() throws JsonProcessingException {

        EmailDTO email = new EmailDTO("test@example.com", "Assunto Teste", "Corpo Teste");

        // Programando o comportamento do mock
        String conteudo = objectMapper.writeValueAsString(email);
        when(objectMapper.writeValueAsString(any(EmailDTO.class))).thenReturn(conteudo);

        // Agir
        kafkaProducer.enviarMensagemTransacaoCliente(email);

        // Afirmar
        verify(kafkaTemplate, times(1)).send(kafkaProducer.getTransacaoResquestTopic(), conteudo);
    }

    // Enviar mensagem de retorno de chamada com sucesso para o tópico de solicitação de retorno de chamada
    @Test
    public void testEnviaMensagemCallbackParaCallbackRequestTopic() throws JsonProcessingException {

        CallbackDTO callback = new CallbackDTO("https://example.com/callback", "Mensagem Teste");

        // Programando o comportamento do mock
        String conteudo = objectMapper.writeValueAsString(callback);
        when(objectMapper.writeValueAsString(any(CallbackDTO.class))).thenReturn(conteudo);

        // Agir
        kafkaProducer.enviarMensagemTransacaoEmpresa(callback);

        // Afirmar
        verify(kafkaTemplate, times(1)).send(kafkaProducer.getCallbackRequestTopic(), conteudo);
    }

    // Verifica se em um formato padrão, o método não acaba por tendo que lidar com JsonProcessingException
    // ao converter o objeto EmailDTO em string
    @Test
    public void testEnvioMensagemEmailConvertidaEmStringSemExcecao() throws JsonProcessingException {

        EmailDTO email = new EmailDTO("test@example.com", "Assunto Teste", "Corpo Teste");

        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);

        // Agir e Afirmar
        assertDoesNotThrow(() -> {
            kafkaProducerMock.enviarMensagemTransacaoCliente(email);
        });

        // Verificar se o método foi chamado com os argumentos corretos
        verify(kafkaProducerMock).enviarMensagemTransacaoCliente(email);
    }

    // Verifica se em um formato padrão, o método não acaba por tendo que lidar com JsonProcessingException
    // ao converter o objeto CallbackDTO em string
    @Test
    public void testEnvioMensagemCallbackConvertidaEmStringSemExcecao() throws JsonProcessingException {

        CallbackDTO callback = new CallbackDTO("https://example.com/callback", "Mensagem Teste");

        KafkaProducerImpl kafkaProducerMock = mock(KafkaProducerImpl.class);

        // Agir e Afirmar
        assertDoesNotThrow(() -> {
            kafkaProducerMock.enviarMensagemTransacaoEmpresa(callback);
        });

        // Verificar se o método foi chamado com os argumentos corretos
        verify(kafkaProducerMock).enviarMensagemTransacaoEmpresa(callback);
    }
}
