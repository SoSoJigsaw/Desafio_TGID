package tgid.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.entity.Empresa;
import tgid.exception.objetosExceptions.CnpjInvalidoException;
import tgid.exception.objetosExceptions.TaxaInvalidoException;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTests {

    // can register a new empresa with valid CNPJ
    @Test
    public void test_register_empresa_with_valid_cnpj() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        // Mock CNPJ validation
        when(cnpjValidator.isValid(anyString(), any())).thenReturn(true);

        // Act
        ResponseEntity<?> response = empresaController.registrarEmpresa(empresa);

        // Assert
        assertEquals(ResponseEntity.ok("Cadastro realizado com sucesso!"), response);
        verify(empresaService).registrarEmpresa("valid_cnpj", "Test Empresa", 1000.0, 0.1, 0.2);
    }

    // can update taxaDeposito and taxaSaque for an existing empresa
    @Test
    public void test_update_taxa_for_existing_empresa() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        Long empresaId = 1L;
        String tipoTaxa = "DEPÓSITO";
        double valor = 0.2;

        // Act
        ResponseEntity<?> response = empresaController.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Assert
        assertEquals(ResponseEntity.ok("Taxa associada com sucesso"), response);
        verify(empresaService).mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);
    }

    // can list all empresas
    @Test
    public void test_list_all_empresas() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa());
        empresas.add(new Empresa());

        // Mock empresaService.listarTodasEmpresas()
        when(empresaService.listarTodasEmpresas()).thenReturn(empresas);

        // Act
        List<Empresa> result = empresaController.listarTodasEmpresas();

        // Assert
        assertEquals(empresas, result);
    }

    // throws CnpjInvalidoException when registering empresa with invalid CNPJ
    @Test
    public void test_throw_cnpj_invalido_exception_when_registering_with_invalid_cnpj() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        Empresa empresa = new Empresa();
        empresa.setCnpj("invalid_cnpj");

        // Mock CNPJ validation
        when(cnpjValidator.isValid(anyString(), any())).thenReturn(false);

        // Act and Assert
        assertThrows(CnpjInvalidoException.class, () -> empresaController.registrarEmpresa(empresa));
    }

    // throws TaxaInvalidoException when updating taxa with invalid type
    @Test
    public void test_throw_taxa_invalido_exception_when_updating_with_invalid_type() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        Long empresaId = 1L;
        String tipoTaxa = "INVALID_TYPE";
        double valor = 0.2;

        // Act and Assert
        assertThrows(TaxaInvalidoException.class, () -> empresaController.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor));
    }

    // throws exception when trying to update taxa for non-existing empresa
    @Test
    public void test_throw_exception_when_updating_taxa_for_non_existing_empresa() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator);
        Long empresaId = 1L;
        String tipoTaxa = "DEPÓSITO";
        double valor = 0.2;

        // Mock empresaService.mudarTaxaValorEmpresa() to throw exception
        doThrow(new RuntimeException()).when(empresaService).mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> empresaController.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor));
    }

}
