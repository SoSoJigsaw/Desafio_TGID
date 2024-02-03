package tgid.notification;

import tgid.dto.EmailDTO;

public interface NotificacaoCliente {
    void enviarNotificacaoKafka(String destinatario, String assunto, String corpo);

}

