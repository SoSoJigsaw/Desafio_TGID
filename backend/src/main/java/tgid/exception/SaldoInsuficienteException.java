package tgid.exception;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String tipo) {
        super("Saldo insuficiente para realizar a transação de " + tipo);
    }
}

