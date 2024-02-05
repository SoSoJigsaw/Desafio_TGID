package tgid.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tgid.exception.NotificacaoEmpresaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class NotificacaoEmpresaImpl implements NotificacaoEmpresa {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEmpresa.class);
    private final RestTemplate restTemplate;

    public NotificacaoEmpresaImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void enviarCallbackParaEmpresa(String url, String mensagem) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(mensagem, headers);

            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
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

    @Override
    public String formatCallbackSucesso(String taxaNome, int valor, String clienteNome,
                                         String empresaNome, LocalDateTime dataTransacao,
                                         Double saldo) {

        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();

        return "Transação recebida: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacaoFormatada + ".\n " +
                "Saldo atual da empresa " + empresaNome + ": " +
                saldoInt + " reais.";
    }

    @Override
    public String formatCallbackFalhaEmpresa(String taxaNome, int valor, String clienteNome,
                                        String empresaNome, LocalDateTime dataTransacao,
                                        Double saldo, Double saldoVirtual) {

        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();
        int saldoVirtualInt = saldoVirtual.intValue();

        return "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacaoFormatada + ".\n " +
                "Não pôde ser concluída por falta atual de saldo de sua empresa. \nSaldo atual da empresa " +
                empresaNome + ": " + saldoInt + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtualInt;
    }

    @Override
    public String formatCallbackFalhaCliente(String taxaNome, int valor, String clienteNome,
                                      String empresaNome, LocalDateTime dataTransacao,
                                      Double saldo, Double saldoVirtual) {

        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();
        int saldoVirtualInt = saldoVirtual.intValue();

        return "Tentativa de transação: " + taxaNome + " de " + valor + " reais."
                + "\n Feita por: " + clienteNome + ". \nData: " + dataTransacaoFormatada + ".\n " +
                "Não pôde ser concluída por falta atual de saldo do cliente. \nSaldo atual da empresa " +
                empresaNome + ": " + saldoInt + " reais. \nSaldo caso a transação se concretizasse: "
                + saldoVirtualInt;
    }

}
