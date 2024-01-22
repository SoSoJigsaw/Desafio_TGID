package tgid.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tgid.exception.objetosExceptions.NotificacaoEmpresaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;


@Service
public class NotificacaoEmpresa {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEmpresa.class);

    @Autowired
    private RestTemplate restTemplate;

    public void enviarCallbackParaEmpresa(String url, String mensagem) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);

            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {

                logger.info("Callback enviado com sucesso. Resposta: {}", response.getBody());

            } else {

                throw new NotificacaoEmpresaException("Erro ao enviar callback. Status code: " + response.getStatusCode());

            }

        } catch (HttpServerErrorException e) {

            logger.error("Erro no servidor ao enviar callback. Status code: {}", e.getStatusCode(), e);

        } catch (UnknownHttpStatusCodeException e) {

            logger.error("Erro desconhecido ao enviar callback. Status code: {}", e.getStatusCode(), e);

        } catch (Exception e) {

            throw new NotificacaoEmpresaException("Erro ao enviar callback: " + e.getMessage());
        }

    }

}
