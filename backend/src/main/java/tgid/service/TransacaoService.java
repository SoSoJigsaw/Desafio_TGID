package tgid.service;

import java.util.List;
import tgid.dto.TransacaoDTO;

public interface TransacaoService {
    void realizarDeposito(Long empresaId, Long clienteId, double valor);

    void realizarSaque(Long empresaId, Long clienteId, double valor);

    List<TransacaoDTO> listarTodasTransacoes();

    void deleteTransacao(Long id);
}

