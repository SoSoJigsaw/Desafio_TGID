package tgid.notification;

import java.time.LocalDateTime;

public interface NotificacaoEmpresa {
    void enviarCallbackParaEmpresa(String url, String mensagem);

    String formatCallbackSucesso(String taxaNome, int valor, String clienteNome,
                                  String empresaNome, LocalDateTime dataTransacao,
                                  Double saldo);

    String formatCallbackFalhaEmpresa(String taxaNome, int valor, String clienteNome,
                                      String empresaNome, LocalDateTime dataTransacao,
                                      Double saldo, Double saldoVirtual);

    String formatCallbackFalhaCliente(String taxaNome, int valor, String clienteNome,
                                      String empresaNome, LocalDateTime dataTransacao,
                                      Double saldo, Double saldoVirtual);
}

