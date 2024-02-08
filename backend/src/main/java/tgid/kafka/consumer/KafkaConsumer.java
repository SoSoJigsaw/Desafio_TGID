package tgid.kafka.consumer;

public interface KafkaConsumer {

    void processarMensagemTransacaoCliente(String conteudo);

    void enviarEmailAoUsuario(String destinatario, String assunto, String corpo);

    void processarMensagemTransacaoEmpresa(String conteudo);

    void enviarCallbackEmpresa(String url, String mensagem);

}
