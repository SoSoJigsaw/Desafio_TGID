package tgid.exception;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String message) {
        super("Saldo insuficiente para realizar a transação");
    }
}

