package tgid.exception.objetosExceptions;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String message) {
        super("Saldo insuficiente para realizar a transação");
    }
}

