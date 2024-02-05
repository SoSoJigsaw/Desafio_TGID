package tgid.notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tgid.exception.NotificacaoEmpresaException;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotificacaoEmpresaImplTest {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEmpresaImplTest.class);


    // Lança NotificacaoEmpresaException com uma mensagem de erro se o código de status da resposta não for 2xx.
    @Test
    public void test_throwException_invalidStatusCode() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://example.com";
        String mensagem = "Test message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class)).thenReturn(responseEntity);

        // Act and Assert
        assertThrows(NotificacaoEmpresaException.class, () -> {
            notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);
        });
    }

    // Lança NotificacaoEmpresaException com uma mensagem de erro se a mensagem fornecida for nula ou vazia.
    @Test
    public void test_throwException_invalidMessage() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://example.com";
        String mensagem = null;

        // Act and Assert
        assertThrows(NotificacaoEmpresaException.class, () -> {
            notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);
        });
    }

}
