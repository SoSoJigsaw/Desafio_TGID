package tgid.exception;

public class ClienteRegistroException extends RuntimeException {

    public ClienteRegistroException() {
        super();
    }

    public ClienteRegistroException(String message) {
        super(message);
    }

    public ClienteRegistroException(String message, Throwable cause) {
        super(message, cause);
    }

}
