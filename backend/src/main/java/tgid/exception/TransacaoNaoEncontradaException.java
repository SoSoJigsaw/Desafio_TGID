package tgid.exception;

public class TransacaoNaoEncontradaException extends RuntimeException {

    public TransacaoNaoEncontradaException() {
        super();
    }

    public TransacaoNaoEncontradaException(String message) {
        super(message);
    }

    public TransacaoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

}



