package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tgid.dto.CallbackDTO;
import tgid.kafka.producer.KafkaProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class NotificacaoEmpresaImpl implements NotificacaoEmpresa {

    private final KafkaProducer kafkaProducer;

    public NotificacaoEmpresaImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void enviarCallbackKafka(String url, String mensagem) {

        if (url == null || url.isEmpty()) {
            log.error("Erro ao enviar callback empresa para processamento do KafkaListener. URL é nulo");
            throw new IllegalArgumentException("O Url não pode ser nulo");
        }
        if (mensagem == null || mensagem.isEmpty()) {
            log.error("Erro ao enviar callback empresa para processamento do KafkaListener. Mensagem é nula");
            throw new IllegalArgumentException("A mensagem não pode ser nula");
        }

        CallbackDTO callbackDTO = new CallbackDTO(url, mensagem);

        try {
            kafkaProducer.enviarMensagemTransacaoEmpresa(callbackDTO);
        } catch (JsonProcessingException e) {
            log.error("Houve um erro ao solicitar o callback: " + e.getMessage());
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
