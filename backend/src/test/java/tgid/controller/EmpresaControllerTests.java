package tgid.controller;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest(EmpresaController.class)
public class EmpresaControllerTests {

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

    // Pode atualizar taxaDeposito e taxaSaque para uma empresa existente
    @Test
    public void test_atualizar_taxa_para_empresa_existente() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);

        TaxaDTO taxaDTO = mock(TaxaDTO.class);
        taxaDTO.setId(1L);
        taxaDTO.setTipoTaxa("DEPÓSITO");
        taxaDTO.setValor(0.2);

        // Act
        ResponseEntity<?> response = empresaController.mudarTaxaValorEmpresa(taxaDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Taxa associada com sucesso"), response);
        verify(empresaService).mudarTaxaValorEmpresa(taxaDTO.getId(), taxaDTO.getTipoTaxa(), taxaDTO.getValor());
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

    // Lança CnpjInvalidoException ao registrar empresa com CNPJ inválido
    @Test
    public void test_lancar_cnpj_invalido_exception_ao_registrar_com_cnpj_invalido() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);
        Empresa empresa = new Empresa();
        empresa.setCnpj("invalid_cnpj");

        // Simular validação de CNPJ
        when(cnpjValidator.isValid(anyString(), any())).thenReturn(false);

        // Act and Assert
        assertThrows(CnpjInvalidoException.class, () -> empresaController.registrarEmpresa(empresa));
    }

    // Lança TaxaInvalidoException ao atualizar com tipo inválido
    @Test
    public void test_lancar_taxa_invalido_exception_ao_atualizar_com_tipo_invalido() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);

        TaxaDTO taxaDTO = mock(TaxaDTO.class);
        taxaDTO.setId(1L);
        taxaDTO.setTipoTaxa("TIPO_INVÁLIDO");
        taxaDTO.setValor(0.2);

        // Act and Assert
        assertThrows(TaxaInvalidoException.class, () -> empresaController.mudarTaxaValorEmpresa(taxaDTO));
    }

    // Lança exceção ao tentar atualizar taxa para empresa não existente
    @Test
    public void test_lancar_excecao_ao_atualizar_taxa_para_empresa_nao_existente() {
        // Arrange
        EmpresaService empresaService = mock(EmpresaService.class);
        CNPJValidator cnpjValidator = mock(CNPJValidator.class);
        TaxaValidator taxaValidator = mock(TaxaValidator.class);
        EmpresaController empresaController = new EmpresaController(empresaService, cnpjValidator, taxaValidator);

        TaxaDTO taxaDTO = mock(TaxaDTO.class);
        taxaDTO.setId(1L);
        taxaDTO.setTipoTaxa("DEPÓSITO");
        taxaDTO.setValor(0.2);

        // Simular empresaService.mudarTaxaValorEmpresa() para lançar exceção
        doThrow(new RuntimeException()).when(empresaService).mudarTaxaValorEmpresa(taxaDTO.getId(), taxaDTO.getTipoTaxa(), taxaDTO.getValor());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> empresaController.mudarTaxaValorEmpresa(taxaDTO));
    }

}
