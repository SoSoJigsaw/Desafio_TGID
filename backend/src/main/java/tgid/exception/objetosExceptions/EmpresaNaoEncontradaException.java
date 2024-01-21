package tgid.exception.objetosExceptions;

public class EmpresaNaoEncontradaException extends RuntimeException {

    public EmpresaNaoEncontradaException(String cnpj) {
        super("Empresa não encontrada para CNPJ: " + cnpj);
    }
}
