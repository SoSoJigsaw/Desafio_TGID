package tgid.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.exception.objetosExceptions.CnpjInvalidoException;
import tgid.exception.objetosExceptions.TaxaInvalidoException;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @Autowired
    CNPJValidator cnpjValidator;

    @CrossOrigin
    @PostMapping("/registrar-empresa")
    public ResponseEntity<?> registrarEmpresa(@RequestBody @Valid Empresa empresa) {

        // Validação do CNPJ
        if (!cnpjValidator.isValid(empresa.getCnpj(), null)) {

            throw new CnpjInvalidoException("CNPJ inválido");

        }

        empresaService.registrarEmpresa(empresa.getCnpj(), empresa.getNome(), empresa.getSaldo(),
                                        empresa.getTaxaDeposito(), empresa.getTaxaSaque());

        return ResponseEntity.ok("Cadastro realizado com sucesso!");

    }

    @CrossOrigin
    @PostMapping("/atualizar-taxa/{empresaId}/{tipoTaxa}/{valor}")
    public ResponseEntity<?> mudarTaxaValorEmpresa(@PathVariable("empresaId") Long empresaId,
                                                   @PathVariable("tipoTaxa") String tipoTaxa,
                                                   @PathVariable("valor") double valor) {

        if (!Objects.equals(tipoTaxa, "DEPÓSITO") || !Objects.equals(tipoTaxa, "SAQUE")) {

            throw new TaxaInvalidoException("Taxa inválida");
        }

        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        return ResponseEntity.ok("Taxa associada com sucesso");

    }

    @CrossOrigin
    @GetMapping("listar-empresas")
    public List<Empresa> listarTodasEmpresas() {

        return empresaService.listarTodasEmpresas();

    }

    @CrossOrigin
    @GetMapping("delete-empresa/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable("id") Long id) {

        empresaService.deleteEmpresa(id);

        return ResponseEntity.ok("Empresa deletada com sucesso!");

    }

}
