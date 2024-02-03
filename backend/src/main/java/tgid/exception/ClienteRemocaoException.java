package tgid.exception;

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
