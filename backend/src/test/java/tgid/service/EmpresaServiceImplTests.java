package tgid.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import tgid.entity.Empresa;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;
import tgid.exception.TaxaInvalidoException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestConfiguration
@Import(EmpresaServiceImpl.class)
public class EmpresaServiceImplTests {

    @InjectMocks
    EmpresaService empresaService;

    @Mock
    EmpresaRepository empresaRepository;

    @Mock
    TransacaoRepository transacaoRepository;


    // Registra uma nova empresa com dados válidos
    @Test
    public void test_registers_new_company_with_valid_data() {
        // Arrange
        String cnpj = "12345678901234";
        String nome = "Company A";
        Double saldo = 1000.0;
        double taxaDeposito = 0.01;
        double taxaSaque = 0.02;

        // Act
        empresaService.registrarEmpresa(cnpj, nome, saldo, taxaDeposito, taxaSaque);

        // Assert
        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    // Atualiza o valor da taxa de depósito para uma empresa
    @Test
    public void test_updates_deposit_tax_value_for_company() {
        // Arrange
        Long empresaId = 1L;
        String tipoTaxa = "DEPÓSITO";
        double valor = 0.02;

        // Act
        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Assert
        verify(empresaRepository, times(1)).atualizarTaxaDeposito(empresaId, valor);
    }

    // Atualiza o valor da taxa de saque para uma empresa
    @Test
    public void test_updates_withdrawal_tax_value_for_company() {
        // Arrange
        Long empresaId = 1L;
        String tipoTaxa = "SAQUE";
        double valor = 0.03;

        // Act
        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Assert
        verify(empresaRepository, times(1)).atualizarTaxaSaque(empresaId, valor);
    }

    // Lança exceção ao tentar atualizar o valor da taxa com tipo inválido
    @Test
    public void test_throws_exception_when_trying_to_update_tax_value_with_invalid_type() {
        // Arrange
        Long empresaId = 1L;
        String tipoTaxa = "INVALIDO";
        double valor = 0.02;

        // Act & Assert
        assertThrows(TaxaInvalidoException.class, () -> empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor));
    }

    // Lança exceção ao tentar excluir uma empresa inexistente
    @Test
    public void test_throws_exception_when_trying_to_delete_non_existent_company() {
        // Arrange
        Long empresaId = 1L;
        when(empresaRepository.getReferenceById(empresaId)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> empresaService.deleteEmpresa(empresaId));
        verify(transacaoRepository, never()).deleteApartirDaEmpresa(any(Empresa.class));
        verify(empresaRepository, never()).delete(any(Empresa.class));
    }

    // Lança exceção ao tentar excluir uma empresa com ID nulo
    @Test
    public void test_throws_exception_when_trying_to_delete_company_with_null_id() {
        // Arrange
        Long empresaId = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> empresaService.deleteEmpresa(empresaId));
        verify(transacaoRepository, never()).deleteApartirDaEmpresa(any(Empresa.class));
        verify(empresaRepository, never()).delete(any(Empresa.class));
    }

}
