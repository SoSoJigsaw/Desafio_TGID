package tgid.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @CNPJ
    private String cnpj;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false, scale = 2)
    private Double taxaDeposito;

    @Column(nullable = false, scale= 2)
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
