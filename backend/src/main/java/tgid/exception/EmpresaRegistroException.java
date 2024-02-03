package tgid.exception;

public class EmpresaRegistroException extends RuntimeException {

    public EmpresaRegistroException() {
        super();
    }

    public EmpresaRegistroException(String message) {
        super(message);
    }

    public EmpresaRegistroException(String message, Throwable cause) {
        super(message, cause);
    }

}
