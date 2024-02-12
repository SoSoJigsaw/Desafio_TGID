package tgid.service;

import org.springframework.http.ResponseEntity;
import tgid.dto.TransacaoDTO;

import java.util.List;

public interface TransacaoService {
    ResponseEntity<?> realizarDeposito(Long empresaId, Long clienteId, Double valor);

    ResponseEntity<?> realizarSaque(Long empresaId, Long clienteId, Double valor);

    List<TransacaoDTO> listarTodasTransacoes();

    void deleteTransacao(Long id);
}

