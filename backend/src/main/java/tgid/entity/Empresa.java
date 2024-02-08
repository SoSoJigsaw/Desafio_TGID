package tgid.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tgid.validation.CNPJ;

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

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotNull
    private Double saldo;

    @Column(nullable = false, scale = 2)
    @NotNull
    private Double taxaDeposito;

    @Column(nullable = false, scale= 2)
    @NotNull
    private Double taxaSaque;

    public Empresa(String cnpj, String nome, Double saldo, double taxaDeposito, double taxaSaque) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.saldo = saldo;
        this.taxaDeposito = taxaDeposito;
        this.taxaSaque = taxaSaque;
    }

    public Empresa() {

    }
}
