package tgid.exception;

public class ClienteRegistroException extends RuntimeException {
    public ClienteRegistroException(Exception e) {

        super("O cliente não pôde ser registrado: " + e.getMessage());
    }

    public ClienteRegistroException(String mensagem) {
        super(mensagem);
    }

}
