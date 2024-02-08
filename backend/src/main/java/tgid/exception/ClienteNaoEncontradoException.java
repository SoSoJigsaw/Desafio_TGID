package tgid.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Exception e) {

        super("A lista de clientes não pôde ser encontrada: " + e.getMessage());

    }

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
