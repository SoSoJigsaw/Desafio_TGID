package tgid.exception;

public class TransacaoNaoEncontradaException extends RuntimeException {

    public TransacaoNaoEncontradaException(Exception e) {

        super("A lista de Transações não pôde ser encontrada: " + e.getMessage());
    }

    public TransacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}



