package tgid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.dto.TransacaoDTO;
import tgid.entity.Cliente;
import tgid.entity.Transacao;
import tgid.service.TransacaoService;

import java.util.List;

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
    public void deposito(@PathVariable("empresaId") Long empresaId,
                         @PathVariable("clienteId") Long clienteId,
                         @RequestBody double valor) {

        transacaoService.realizarDeposito(empresaId, clienteId, valor);
    }

    @PostMapping("/saque/{empresaId}/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public void saque(@PathVariable("empresaId") Long empresaId,
                      @PathVariable("clienteId") Long clienteId,
                      @RequestBody double valor) {

        transacaoService.realizarSaque(empresaId, clienteId, valor);
    }

    @GetMapping("listar-transacoes")
    @ResponseStatus(HttpStatus.OK)
    public List<TransacaoDTO> listarTodasTransacoes() {

        return transacaoService.listarTodasTransacoes();

    }

    @DeleteMapping("delete-transacao/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteTransacao(@PathVariable("id") Long id) {

        transacaoService.deleteTransacao(id);

        return ResponseEntity.ok("Transação deletada com sucesso!");

    }

}
