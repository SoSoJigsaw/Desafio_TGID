package tgid.exception;

public class CpfInvalidoException extends RuntimeException {

    public CpfInvalidoException() {

        super("CPF Inválido");
    }

    public CpfInvalidoException(Exception e) {

        super("A validação do CPF não pôde ser realizada: " + e.getMessage());
    }

}
