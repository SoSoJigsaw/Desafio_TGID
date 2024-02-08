package tgid.exception;

import org.springframework.http.HttpStatusCode;

public class NotificacaoEmpresaException extends RuntimeException {

    public NotificacaoEmpresaException(Exception e) {

        super("Falha no envio de notificação para a empresa: " + e.getMessage());
    }

    public NotificacaoEmpresaException(HttpStatusCode status) {
        super("Erro ao enviar callback. Status code: " + status);
    }

}

