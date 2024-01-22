package tgid.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class NotificacaoCliente {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoCliente.class);

    @Autowired
    JavaMailSender javaMailSender;

    public void enviarNotificacaoPorEmail(String destinatario, String assunto, String corpo) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        try {

            javaMailSender.send(mensagem);
            logger.info("E-mail enviado com sucesso para: {}", destinatario);

        } catch (Exception e) {

            throw new RuntimeException("Erro ao enviar e-mail", e);

        }

    }

}

