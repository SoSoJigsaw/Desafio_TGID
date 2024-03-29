package tgid.exception;

public class EmpresaRegistroException extends RuntimeException {

    public EmpresaRegistroException(Exception e) {

        super("A empresa não pôde ser registrada: " + e.getMessage());
    }

    public EmpresaRegistroException(String mensagem) {
        super(mensagem);
    }

    public EmpresaRegistroException(String mensagem, NullPointerException e) {

        super("A empresa não pôde ser registrada: " + mensagem);

    }

}
