package tgid.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tgid.validation.CNPJ;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "Empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    @CNPJ
    private String cnpj;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false)
    private Double taxaDeposito;

    @Column(nullable = false)
    private Double taxaSaque;

}
