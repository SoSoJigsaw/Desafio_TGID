package tgid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

    private Long id;
    private String tipo;
    private double valor;
    private String dataTransacao;
    private String clienteNome;
    private String empresaNome;

}
