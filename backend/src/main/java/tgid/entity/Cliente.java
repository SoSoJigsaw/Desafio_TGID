package tgid.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tgid.validation.CPF;


@Getter
@Setter

@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 14)
    @NotBlank
    @CPF
    private String cpf;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nome;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    private Double saldo;

    public Cliente(String cpf, String nome, String email, Double saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
    }

    public Cliente() {

    }
}
