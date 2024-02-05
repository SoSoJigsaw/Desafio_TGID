package tgid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoInsuficienteDTO {

    private String entidade;
    private String nome;
    private int saldo;
    private String operacao;
    private int valorTransacao;

    public SaldoInsuficienteDTO(String entidade, String nome, int saldo, String operacao, int valorTransacao) {
        this.entidade = entidade;
        this.nome = nome;
        this.saldo = saldo;
        this.operacao = operacao;
        this.valorTransacao = valorTransacao;
    }
}
