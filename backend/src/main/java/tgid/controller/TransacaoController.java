package tgid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Transacao;
import tgid.service.TransacaoService;

import java.util.List;

@RestController
@RequestMapping
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/deposito/{empresaId}/{clienteId}/{valor}")
    public void deposito(@PathVariable("empresaId") Long empresaId,
                         @PathVariable("clienteId") Long clienteId,
                         @PathVariable("valor") double valor) {

        transacaoService.realizarDeposito(empresaId, clienteId, valor);
    }

    @GetMapping("/saque/{empresaId}/{clienteId}/{valor}")
    public void saque(@PathVariable("empresaId") Long empresaId,
                      @PathVariable("clienteId") Long clienteId,
                      @PathVariable("valor") double valor) {

        transacaoService.realizarSaque(empresaId, clienteId, valor);
    }

    @CrossOrigin
    @GetMapping("listar-transacoes")
    public List<TransacaoDTO> listarTodasTransacoes() {

        return transacaoService.listarTodasTransacoes();

    }

    @CrossOrigin
    @GetMapping("delete-transacao/{id}")
    public ResponseEntity<?> deleteTransacao(@PathVariable("id") Long id) {

        transacaoService.deleteTransacao(id);

        return ResponseEntity.ok("Transação deletada com sucesso!");

    }

}
