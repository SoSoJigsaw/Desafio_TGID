package tgid.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tgid.dto.EmpresaDTO;
import tgid.dto.TaxaDTO;
import tgid.exception.EmpresaRegistroException;
import tgid.service.EmpresaService;
import tgid.validation.CNPJValidator;
import tgid.validation.TaxaValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpresaControllerTest {

    @Mock
    private EmpresaService empresaService;

    @Mock
    private CNPJValidator cnpjValidator;

    @Mock
    private TaxaValidator taxaValidator;

    @InjectMocks
    private EmpresaController empresaController;

    // pode registrar uma nova empresa com CNPJ válido
    @Test
    public void testRegistrarNovaEmpresaComCnpjValido() {

        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setCnpj("84867778000192");
        empresaDTO.setNome("Test Company");
        empresaDTO.setSaldo(1000.0);
        empresaDTO.setTaxaDeposito(0.1);
        empresaDTO.setTaxaSaque(0.2);

        // Configurando o comportamento do mock
        when(cnpjValidator.isValid("84867778000192", null)).thenReturn(true);

        ResponseEntity<?> response = empresaController.registrarEmpresa(empresaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cadastro realizado com sucesso!", response.getBody());

        verify(cnpjValidator).isValid("84867778000192", null);
        verify(empresaService).registrarEmpresa("84867778000192", "Test Company", 1000.0, 0.1, 0.2);
    }

    // pode atualizar a taxa de transação para uma empresa
    @Test
    public void testAtualizarTaxaTransacaoParaUmaEmpresa() {

        TaxaDTO taxaDTO = new TaxaDTO();
        taxaDTO.setId(1L);
        taxaDTO.setTipoTaxa("DEPÓSITO");
        taxaDTO.setValor(0.5);

        // Configurando o comportamento do mock
        when(taxaValidator.isValid("DEPÓSITO", null)).thenReturn(true);

        ResponseEntity<?> response = empresaController.mudarTaxaValorEmpresa(taxaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Taxa associada com sucesso", response.getBody());

        verify(taxaValidator).isValid("DEPÓSITO", null);
        verify(empresaService).mudarTaxaValorEmpresa(1L, "DEPÓSITO", 0.5);
    }

    // pode listar todas as empresas registradas
    @Test
    public void testListarTodasEmpresasRegistradas() {

        List<EmpresaDTO> expectedCompanies = new ArrayList<>();
        expectedCompanies.add(new EmpresaDTO(1L, "12345678901234", "Company 1", 1000.0, 0.1, 0.2));
        expectedCompanies.add(new EmpresaDTO(2L, "56789012345678", "Company 2", 2000.0, 0.2, 0.3));

        when(empresaService.listarTodasEmpresas()).thenReturn(expectedCompanies);

        List<EmpresaDTO> companies = empresaController.listarTodasEmpresas();

        assertEquals(expectedCompanies, companies);

        verify(empresaService).listarTodasEmpresas();
    }

    // lança exceção ao cadastrar uma nova empresa com CNPJ inválido
    @Test
    public void testLancarExcecaoQuandoCadastrarNovaEmpresaComCnpjInvalido() {

        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setCnpj("1234567890");
        empresaDTO.setNome("Test Company");
        empresaDTO.setSaldo(1000.0);
        empresaDTO.setTaxaDeposito(0.1);
        empresaDTO.setTaxaSaque(0.2);

        when(cnpjValidator.isValid("1234567890", null)).thenReturn(false);

        assertThrows(EmpresaRegistroException.class, () -> {
            empresaController.registrarEmpresa(empresaDTO);
        });

        verify(cnpjValidator).isValid("1234567890", null);
        verify(empresaService, never()).registrarEmpresa(anyString(), anyString(), anyDouble(), anyDouble(), anyDouble());
    }
}
