package tgid.controller;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import tgid.dto.TaxaDTO;
import tgid.entity.Empresa;
import tgid.exception.CnpjInvalidoException;
import tgid.exception.TaxaInvalidoException;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;
import tgid.validation.TaxaValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTest {

    // Pode registrar uma nova empresa com CNPJ válido
    @Test
    public void test_registrar_empresa_com_cnpj_valido() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        // Simular validação de CNPJ
        when(cnpjValidator.isValid(anyString(), any())).thenReturn(true);

        // Act
        ResponseEntity<?> response = empresaController.registrarEmpresa(empresa);

        // Assert
        assertEquals(ResponseEntity.ok("Cadastro realizado com sucesso!"), response);
        verify(empresaService).registrarEmpresa("valid_cnpj", "Test Empresa", 1000.0, 0.1, 0.2);
    }

    // Pode listar todas as empresas
    @Test
    public void test_listar_todas_as_empresas() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa());
        empresas.add(new Empresa());

        // Simular empresaService.listarTodasEmpresas()
        when(empresaService.listarTodasEmpresas()).thenReturn(empresas);

        // Act
        List<Empresa> result = empresaController.listarTodasEmpresas();

        // Assert
        assertEquals(empresas, result);
    }

}
