package tgid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.dto.TransacaoDTO;
import tgid.exception.TransacaoInvalidaException;
import tgid.exception.TransacaoNaoEncontradaException;
import tgid.exception.TransacaoRemocaoException;
import tgid.service.TransacaoService;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/deposito/{empresaId}/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deposito(@PathVariable("empresaId") Long empresaId,
                         @PathVariable("clienteId") Long clienteId,
                         @RequestBody double valor) {
        try {
            return transacaoService.realizarDeposito(empresaId, clienteId, valor);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TransacaoInvalidaException("depósito", e);
        }
    }

    @PostMapping("/saque/{empresaId}/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saque(@PathVariable("empresaId") Long empresaId,
                      @PathVariable("clienteId") Long clienteId,
                      @RequestBody double valor) {
        try {
            return transacaoService.realizarSaque(empresaId, clienteId, valor);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TransacaoInvalidaException("saque", e);
        }
    }

    @GetMapping("listar-transacoes")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable
    public List<TransacaoDTO> listarTodasTransacoes() {

        try {

            return transacaoService.listarTodasTransacoes();

        } catch (Exception e) {
            throw new TransacaoNaoEncontradaException(e);
        }
    }

    @DeleteMapping("delete-transacao/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteTransacao(@PathVariable("id") Long id) {

        try {
            transacaoService.deleteTransacao(id);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TransacaoRemocaoException(e);
        }

        return ResponseEntity.ok("Transação deletada com sucesso!");
    }

}
