package tgid.exception.objetosExceptions;

public class CnpjInvalidoException extends RuntimeException {

    public CnpjInvalidoException() {
        super();
    }

    public CnpjInvalidoException(String message) {
        super("CNPJ Inválido. " + message);
    }

    public CnpjInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
