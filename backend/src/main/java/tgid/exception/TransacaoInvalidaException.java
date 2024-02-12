package tgid.exception;

public class TransacaoInvalidaException extends RuntimeException {

    public TransacaoInvalidaException(String tipo, Exception e) {

        super("Erro no processamento da transação de " + tipo + ": " + e.getMessage());
    }

    public TransacaoInvalidaException(String tipo, String mensagem, Exception e) {

        super("Erro no processamento da transação de " + tipo + ": " + mensagem);

    }
}
