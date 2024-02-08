package tgid.exception;

public class ClienteRemocaoException extends RuntimeException {

    public ClienteRemocaoException(Exception e) {

        super("O cliente não pôde ser deletado: " + e.getMessage());
    }

    public ClienteRemocaoException(String mensagem) {
        super(mensagem);
    }

}
