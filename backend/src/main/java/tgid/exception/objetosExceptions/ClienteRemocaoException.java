package tgid.exception.objetosExceptions;

public class ClienteRemocaoException extends RuntimeException {

    public ClienteRemocaoException() {
        super();
    }

    public ClienteRemocaoException(String message) {
        super(message);
    }

    public ClienteRemocaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
