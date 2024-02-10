package tgid.exception;

public class TransacaoZeradaException extends RuntimeException {

    public TransacaoZeradaException(String transacao) {
        super("O valor do " + transacao + " não pode ser zero");
    }

}
