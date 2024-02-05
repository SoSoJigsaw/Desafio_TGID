package tgid.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgid.dto.EmailDTO;
import tgid.kafka.producer.KafkaProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificacaoClienteImpl implements NotificacaoCliente {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoCliente.class);

    @Autowired
    public KafkaProducer kafkaProducer;

    public NotificacaoClienteImpl(KafkaProducer kafkaProducerMock) {
        this.kafkaProducer = kafkaProducerMock;
    }

    public NotificacaoClienteImpl() {

    }

    @Override
    public void enviarNotificacaoKafka(String destinatario, String assunto, String corpo) {

        if (destinatario == null || destinatario.isEmpty()) {
            throw new IllegalArgumentException("Destinatario não pode ser nulo");
        }
        if (assunto == null || assunto.isEmpty()) {
            throw new IllegalArgumentException("Assunto não pode ser nulo");
        }
        if (corpo == null || corpo.isEmpty()) {
            throw new IllegalArgumentException("Corpo não pode ser nulo");
        }

        EmailDTO emailDTO = new EmailDTO(destinatario, assunto, corpo);

        try {
            kafkaProducer.enviarMensagemTransacao(emailDTO);
        } catch (JsonProcessingException e) {
            logger.info("Houve um erro ao solicitar o email: " + e.getMessage());
        }
    }

    @Override
    public String formatAssuntoOperacaoRealizada(String tipoTransacao, int valorTransacao) {
        return tipoTransacao + " de " + valorTransacao + " reais concluída";
    }

    @Override
    public String formatCorpoOperacaoRealizada(String tipoTransacao, int valorTransacao,
                                               String nomeEmpresa, LocalDateTime dataTransacao,
                                               Double saldo) {

        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();

        return "Seu " + tipoTransacao + " no valor de " + valorTransacao + " reais na empresa " +
                nomeEmpresa + " foi concluída com sucesso. " +
                "\n Seu saldo agora é de " + saldoInt + " reais." +
                "\n Data/Hora da operação: " + dataTransacaoFormatada;
    }

    @Override
    public String formatAssuntoOperacaoNegada(String tipoTransacao, int valorTransacao) {
        return "Operação de " + tipoTransacao + " negada";
    }

    @Override
    public String formatCorpoOperacaoNegadaEmpresa(String tipoTransacao, int valorTransacao,
                                                   String nomeEmpresa, LocalDateTime dataTransacao,
                                                   Double saldo) {
        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();

        return "Tentativa de " + tipoTransacao + " no valor de " + valorTransacao + " reais na empresa " +
                nomeEmpresa + " não pôde ser concluída. A empresa não possui saldo suficiente para a transação. " +
                "\n Seu saldo continua sendo de " + saldoInt + " reais." +
                "\n Data/Hora da operação: " + dataTransacaoFormatada;
    }

    @Override
    public String formatCorpoOperacaoNegadaCliente(String tipoTransacao, int valorTransacao,
                                            String nomeEmpresa, LocalDateTime dataTransacao,
                                            Double saldo) {
        String dataTransacaoFormatada = dataTransacao.format(DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss"));
        int saldoInt = saldo.intValue();

        return "Tentativa de " + tipoTransacao + " no valor de " + valorTransacao + " reais na empresa " +
                nomeEmpresa + " não pôde ser concluída. Você não possui saldo suficiente para a transação. " +
                "\n Seu saldo continua sendo de " + saldoInt + " reais." +
                "\n Data/Hora da operação: " + dataTransacaoFormatada;
    }

}
