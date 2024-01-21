package tgid.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(KafkaException.class);
    public KafkaException(Throwable ex) {

        if (ex.getCause() instanceof org.apache.kafka.common.errors.RecordTooLargeException) {

            logger.error("Erro: Tamanho da mensagem excede o limite no Kafka.");

        } else if (ex.getCause() instanceof org.apache.kafka.common.errors.SerializationException) {

            logger.error("Erro: Problema de serialização no Kafka.");

        } else if (ex.getCause() instanceof org.apache.kafka.common.errors.TimeoutException) {

            logger.error("Erro: Tempo limite excedido no Kafka.");

        } else if (ex.getCause() instanceof org.apache.kafka.common.errors.ProducerFencedException) {

            logger.error("Erro: Produtor foi encerrado ou fechado no Kafka.");

        } else if (ex.getCause() instanceof org.apache.kafka.common.errors.NotLeaderForPartitionException) {

            logger.error("Erro: Este nó não é o líder para a partição no Kafka.");

        } else if (ex.getCause() instanceof org.apache.kafka.common.errors.InterruptException) {

            logger.error("Erro: Operação interrompida no Kafka.");

        } else {

            logger.error("Erro desconhecido ao enviar mensagem para Kafka.", ex);

        }

    }

}
