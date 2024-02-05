package tgid.notification;

import tgid.dto.EmailDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificacaoCliente {
    void enviarNotificacaoKafka(String destinatario, String assunto, String corpo);

    String formatAssuntoOperacaoRealizada(String tipoTransacao, int valorTransacao);

    String formatCorpoOperacaoRealizada(String tipoTransacao, int valorTransacao,
                                        String nomeEmpresa, LocalDateTime dataTransacao,
                                        Double saldo);

    String formatAssuntoOperacaoNegada(String tipoTransacao, int valorTransacao);

    String formatCorpoOperacaoNegadaEmpresa(String tipoTransacao, int valorTransacao,
                                            String nomeEmpresa, LocalDateTime dataTransacao,
                                            Double saldo);

    String formatCorpoOperacaoNegadaCliente(String tipoTransacao, int valorTransacao,
                                            String nomeCliente, LocalDateTime dataTransacao,
                                            Double saldo);
}

