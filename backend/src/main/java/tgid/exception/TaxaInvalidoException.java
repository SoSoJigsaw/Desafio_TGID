package tgid.exception;

public class TaxaInvalidoException extends RuntimeException {

    public TaxaInvalidoException() {
        super();
    }

    public TaxaInvalidoException(String message) {
        super(message);
    }

    public TaxaInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
