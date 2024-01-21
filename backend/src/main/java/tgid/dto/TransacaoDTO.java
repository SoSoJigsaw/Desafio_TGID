package tgid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class TransacaoDTO {

    private Long id;
    private String tipo;
    private double valor;
    private String dataTransacao;
    private String clienteNome;
    private String empresaNome;

}
