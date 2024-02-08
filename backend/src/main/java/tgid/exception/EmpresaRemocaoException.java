package tgid.exception;

public class EmpresaRemocaoException extends RuntimeException {

    public EmpresaRemocaoException(Exception e) {

        super("A empresa não pôde ser deletada: " + e.getMessage());
    }

    public EmpresaRemocaoException(String mensagem) {
        super(mensagem);
    }

}
