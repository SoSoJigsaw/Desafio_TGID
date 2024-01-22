package tgid.notification;

public interface NotificacaoCliente {
    void enviarNotificacaoPorEmail(String destinatario, String assunto, String corpo);
}

