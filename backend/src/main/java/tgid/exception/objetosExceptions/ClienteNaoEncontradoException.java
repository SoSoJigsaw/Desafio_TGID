package tgid.exception.objetosExceptions;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(String cpf) { super("Cliente não encontrado para CPF: " + cpf); }

}
