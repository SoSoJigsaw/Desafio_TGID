package tgid.exception;

public class EmpresaNaoEncontradaException extends RuntimeException {

    public EmpresaNaoEncontradaException(Exception e) {

        super("A lista de Empresas não pôde ser encontrada: " + e.getMessage());
    }

    public EmpresaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
