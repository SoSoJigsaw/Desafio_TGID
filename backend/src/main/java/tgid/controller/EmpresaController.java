package tgid.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tgid.entity.Cliente;
import tgid.entity.Empresa;
import tgid.exception.objetosExceptions.CnpjInvalidoException;
import tgid.exception.objetosExceptions.TaxaInvalidoException;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final CNPJValidator cnpjValidator;

    public EmpresaController(EmpresaService empresaService, CNPJValidator cnpjValidator) {
        this.empresaService = empresaService;
        this.cnpjValidator = cnpjValidator;
    }

    @PostMapping("/registrar-empresa")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> registrarEmpresa(@RequestBody @Valid Empresa empresa) {

        // Validação do CNPJ
        if (!cnpjValidator.isValid(empresa.getCnpj(), null)) {
            throw new CnpjInvalidoException("CNPJ inválido");
        }

        empresaService.registrarEmpresa(empresa.getCnpj(), empresa.getNome(), empresa.getSaldo(),
                                        empresa.getTaxaDeposito(), empresa.getTaxaSaque());

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PostMapping("/atualizar-taxa/{empresaId}/{tipoTaxa}/{valor}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> mudarTaxaValorEmpresa(@PathVariable("empresaId") Long empresaId,
                                                   @PathVariable("tipoTaxa") String tipoTaxa,
                                                   @PathVariable("valor") double valor) {

        if (!Objects.equals(tipoTaxa, "DEPÓSITO") || !Objects.equals(tipoTaxa, "SAQUE")) {
            throw new TaxaInvalidoException("Taxa inválida");
        }

        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

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
    
    @ControllerAdvice
    public static class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(CnpjInvalidoException.class)
        public ResponseEntity<?> handleCnpjInvalidoException(CnpjInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    

}
