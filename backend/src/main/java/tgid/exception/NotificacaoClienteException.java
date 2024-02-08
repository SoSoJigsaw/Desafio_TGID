package tgid.exception;

public class NotificacaoClienteException extends RuntimeException {

    public NotificacaoClienteException(Exception e) {

        super("Falha no envio de email para o cliente: " + e.getMessage());
    }

}
