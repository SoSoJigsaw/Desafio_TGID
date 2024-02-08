package tgid.exception;

public class TaxaInvalidoException extends RuntimeException {

    public TaxaInvalidoException() {

        super("Tipo de taxa de transação inválida. Valores esperados: DEPÓSITO ou SAQUE");
    }

    public TaxaInvalidoException(Exception e) {

        super("A atualização da taxa falhou: " + e.getMessage());
    }

}
