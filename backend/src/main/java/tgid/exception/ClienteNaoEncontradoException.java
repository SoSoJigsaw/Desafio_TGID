package tgid.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(String cpf) { super("Cliente não encontrado para CPF: " + cpf); }

}
