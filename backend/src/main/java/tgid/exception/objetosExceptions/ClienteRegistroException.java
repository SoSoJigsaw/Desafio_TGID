package tgid.exception.objetosExceptions;

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
