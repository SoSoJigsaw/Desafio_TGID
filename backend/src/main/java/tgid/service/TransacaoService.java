package tgid.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import tgid.dto.TransacaoDTO;

public interface TransacaoService {
    ResponseEntity<?> realizarDeposito(Long empresaId, Long clienteId, double valor);

    ResponseEntity<?> realizarSaque(Long empresaId, Long clienteId, double valor);

    List<TransacaoDTO> listarTodasTransacoes();

    void deleteTransacao(Long id);
}

