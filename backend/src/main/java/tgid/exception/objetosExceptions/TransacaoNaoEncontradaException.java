package tgid.exception.objetosExceptions;

public class TransacaoNaoEncontradaException extends RuntimeException {

    public TransacaoNaoEncontradaException() {
        super();
    }

    public TransacaoNaoEncontradaException(Long id) {
        super("Transação não encontrada para ID: " + id);
    }

    public TransacaoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

}



