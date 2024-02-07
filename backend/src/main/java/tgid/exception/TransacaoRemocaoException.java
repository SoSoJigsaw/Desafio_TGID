package tgid.exception;

import tgid.entity.Transacao;

public class TransacaoRemocaoException extends RuntimeException {

    public TransacaoRemocaoException() {
        super();
    }

    public TransacaoRemocaoException(String message) {
        super(message);
    }

    public TransacaoRemocaoException(String message, Throwable cause) {
        super(message, cause);
    }

}

