package tgid.notification;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tgid.exception.NotificacaoEmpresaException;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@TestConfiguration
@Import(NotificacaoEmpresaImpl.class)
public class NotificacaoEmpresaImplTests {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEmpresaImplTests.class);

    // Envia uma solicitação POST para a URL fornecida com a mensagem e os cabeçalhos dados, e registra uma mensagem de sucesso
    // se o código de status da resposta for 2xx.
    @Test
    public void test_sendPostRequest_success() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://example.com";
        String mensagem = "Test message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Success", HttpStatus.OK);
        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class)).thenReturn(responseEntity);

        // Act
        notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);

        // Assert
        verify(restTemplate).exchange(url, HttpMethod.POST, requestEntity, String.class);
        verify(logger).info("Callback enviado com sucesso. Resposta: {}", responseEntity.getBody());
    }

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

    // Lança NotificacaoEmpresaException com uma mensagem de erro se a URL fornecida for inválida ou inacessível.
    @Test
    public void test_throwException_invalidUrl() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://invalidurl";
        String mensagem = "Test message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);
        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class))
                .thenThrow(UnknownHttpStatusCodeException.class);

        // Act and Assert
        assertThrows(NotificacaoEmpresaException.class, () -> {
            notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);
        });
    }

    // Registra uma mensagem de erro se uma HttpServerErrorException for lançada durante o envio da solicitação POST.
    @Test
    public void test_logError_httpServerErrorException() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://example.com";
        String mensagem = "Test message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);
        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class))
                .thenThrow(HttpServerErrorException.class);

        // Act
        notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);

        // Assert
        verify(logger).error("Erro no servidor ao enviar callback. Status code: {}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Registra uma mensagem de erro se uma UnknownHttpStatusCodeException for lançada durante o envio da solicitação POST.
    @Test
    public void test_logError_unknownHttpStatusCodeException() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        NotificacaoEmpresaImpl notificacaoEmpresa = new NotificacaoEmpresaImpl(restTemplate);
        String url = "http://example.com";
        String mensagem = "Test message";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);
        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class))
                .thenThrow(UnknownHttpStatusCodeException.class);

        // Act
        notificacaoEmpresa.enviarCallbackParaEmpresa(url, mensagem);

        // Assert
        verify(logger).error("Erro desconhecido ao enviar callback. Status code: {}", HttpStatus.INTERNAL_SERVER_ERROR);
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
