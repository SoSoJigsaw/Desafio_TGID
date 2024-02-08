package tgid.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tgid.dto.EmpresaDTO;
import tgid.dto.TaxaDTO;
import tgid.entity.Empresa;
import tgid.exception.*;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;
import tgid.validation.TaxaValidator;

import java.util.List;

@Slf4j
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
    public ResponseEntity<?> registrarEmpresa(@RequestBody @Valid EmpresaDTO empresa) {

        try {
            // Validação do CNPJ
            if (!cnpjValidator.isValid(empresa.getCnpj(), null)) {
                log.error("CNPJ Inválido. Tente novamente");
                throw new CnpjInvalidoException();
            }

            empresaService.registrarEmpresa(empresa.getCnpj(), empresa.getNome(), empresa.getSaldo(),
                    empresa.getTaxaDeposito(), empresa.getTaxaSaque());

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new EmpresaRegistroException(e);
        }

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PostMapping("/atualizar-taxa")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> mudarTaxaValorEmpresa(@RequestBody @Valid TaxaDTO taxaDTO) {

        try {
            if (!taxaValidator.isValid(taxaDTO.getTipoTaxa(), null)) {
                log.info("Taxa Inválida: a taxa deve ser ou DEPÓSITO ou SAQUE");
                throw new TaxaInvalidoException();
            }

            empresaService.mudarTaxaValorEmpresa(taxaDTO.getId(), taxaDTO.getTipoTaxa(), taxaDTO.getValor());

        } catch (Exception e) {
            throw new TaxaInvalidoException(e);
        }

        return ResponseEntity.ok("Taxa associada com sucesso");
    }

    @GetMapping("listar-empresas")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable
    public List<EmpresaDTO> listarTodasEmpresas() {

        try {

            return empresaService.listarTodasEmpresas();

        } catch (Exception e) {
            throw new EmpresaNaoEncontradaException(e);
        }
    }

    @DeleteMapping("delete-empresa/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteEmpresa(@PathVariable("id") Long id) {

        try {
            empresaService.deleteEmpresa(id);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new EmpresaRemocaoException(e);
        }

        return ResponseEntity.ok("Empresa deletada com sucesso!");
    }

}
