package tgid.exception;

public class TransacaoNegativaException extends RuntimeException {

    public TransacaoNegativaException(String transacao) {
        super("O valor do " + transacao + " não pode ser negativo");
    }

}
