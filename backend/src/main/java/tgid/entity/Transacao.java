package tgid.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "Transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String tipo;

    @Column(nullable = false)
    @NotNull
    private double valor;

    @Column
    @NotNull
    @PastOrPresent
    private LocalDateTime dataTransacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @NotNull
    private Empresa empresa;

    public Transacao(double valor, String tipo, LocalDateTime dataTransacao, Cliente cliente, Empresa empresa) {
        this.valor = valor;
        this.tipo = tipo;
        this.dataTransacao = dataTransacao;
        this.cliente = cliente;
        this.empresa = empresa;
    }

    public Transacao() {

    }
}

