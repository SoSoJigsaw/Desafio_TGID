package tgid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxaDTO {

    private Long id;
    private String tipoTaxa;
    private double valor;

}
