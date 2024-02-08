package tgid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private Long id;
    private String cnpj;
    private String nome;
    private Double saldo;
    private Double taxaDeposito;
    private Double taxaSaque;

}
