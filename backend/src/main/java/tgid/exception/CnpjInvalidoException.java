package tgid.exception;

public class CnpjInvalidoException extends RuntimeException {

    public CnpjInvalidoException() {
        super("CNPJ Inválido");
    }

    public CnpjInvalidoException(Exception e) {
        super("A validação do CNPJ não pôde ser realizada: " + e.getMessage());
    }
}
