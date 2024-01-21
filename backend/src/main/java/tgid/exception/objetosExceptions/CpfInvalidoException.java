package tgid.exception.objetosExceptions;

public class CpfInvalidoException extends RuntimeException {

    public CpfInvalidoException() {
        super();
    }

    public CpfInvalidoException(String message) {
        super("CPF Inválido. " + message);
    }

    public CpfInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
