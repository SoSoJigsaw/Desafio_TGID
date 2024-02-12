package tgid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaldoInsuficienteDTO {

    private String entidade;
    private String nome;
    private int saldo;
    private String operacao;
    private int valorTransacao;

}
