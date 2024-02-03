package tgid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {

    private String destinatario;
    private String assunto;
    private String corpo;

    public EmailDTO(String destinatario, String assunto, String corpo) {
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.corpo = corpo;
    }

    public EmailDTO() {};

}
