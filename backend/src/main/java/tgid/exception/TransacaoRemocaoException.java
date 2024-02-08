package tgid.exception;

import tgid.entity.Transacao;

public class TransacaoRemocaoException extends RuntimeException {

    public TransacaoRemocaoException(Exception e) {

        super("O registro de transação não pôde ser deletado: " + e.getMessage());
    }

    public TransacaoRemocaoException(String mensagem) {
        super(mensagem);
    }

}

