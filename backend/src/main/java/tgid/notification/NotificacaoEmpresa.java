package tgid.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Service
public class NotificacaoEmpresa {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEmpresa.class);

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = { HttpServerErrorException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void enviarCallbackParaEmpresa(String url, String mensagem) {

        try {

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null,
                                                                    String.class, mensagem);

            if (response.getStatusCode() == HttpStatus.OK) {

                logger.info("Callback enviado com sucesso. Resposta: {}", response.getBody());

            } else {

                logger.error("Erro ao enviar callback. Status code: {}", response.getStatusCode());

            }

        } catch (HttpServerErrorException e) {

            logger.error("Erro no servidor ao enviar callback. Status code: {}", e.getStatusCode(), e);
            throw e;

        } catch (UnknownHttpStatusCodeException e) {

            logger.error("Erro desconhecido ao enviar callback. Status code: {}", e.getStatusCode(), e);
            throw e;

        } catch (Exception e) {

            logger.error("Erro ao enviar callback: {}", e.getMessage(), e);
            throw e;

        }

    }

}
