package tgid.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.dto.TaxaDTO;
import tgid.entity.Empresa;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;
import tgid.validation.TaxaValidator;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final CNPJValidator cnpjValidator;
    private final TaxaValidator taxaValidator;

    public EmpresaController(EmpresaService empresaService, CNPJValidator cnpjValidator, TaxaValidator taxaValidator) {
        this.empresaService = empresaService;
        this.cnpjValidator = cnpjValidator;
        this.taxaValidator = taxaValidator;
    }

    @PostMapping("/registrar-empresa")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> registrarEmpresa(@RequestBody Empresa empresa) {

        // Validação do CNPJ
        if (!cnpjValidator.isValid(empresa.getCnpj(), null)) {
            System.err.println("CNPJ Inválido. Tente novamente");
            return ResponseEntity.badRequest().body("CNPJ Inválido");
        }

        empresaService.registrarEmpresa(empresa.getCnpj(), empresa.getNome(), empresa.getSaldo(),
                                        empresa.getTaxaDeposito(), empresa.getTaxaSaque());

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PostMapping("/atualizar-taxa")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> mudarTaxaValorEmpresa(@RequestBody TaxaDTO taxaDTO) {

        if (!taxaValidator.isValid(taxaDTO.getTipoTaxa(), null)) {
            System.err.println("Taxa Inválida: a taxa deve ser ou DEPÓSITO ou SAQUE");
            return ResponseEntity.badRequest().body("Taxa Inválida");
        }

        empresaService.mudarTaxaValorEmpresa(taxaDTO.getId(), taxaDTO.getTipoTaxa(), taxaDTO.getValor());

        return ResponseEntity.ok("Taxa associada com sucesso");
    }

    @GetMapping("listar-empresas")
    @ResponseStatus(HttpStatus.OK)
    public List<Empresa> listarTodasEmpresas() {

        return empresaService.listarTodasEmpresas();
    }

    @DeleteMapping("delete-empresa/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteEmpresa(@PathVariable("id") Long id) {

        empresaService.deleteEmpresa(id);

        return ResponseEntity.ok("Empresa deletada com sucesso!");
    }

}
