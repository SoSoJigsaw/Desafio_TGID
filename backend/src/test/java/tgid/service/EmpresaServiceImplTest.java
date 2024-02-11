package tgid.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tgid.entity.Empresa;
import tgid.exception.EmpresaNaoEncontradaException;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private EmpresaServiceImpl empresaService;

    // pode alterar taxaDeposito de uma empresa existente
    @Test
    public void testMudarTaxaDepositoDeEmpresaExistente() {
        // Arranjo
        Long empresaId = 1L;
        String tipoTaxa = "DEPÓSITO";
        double valor = 0.2;

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(empresaRepository.getReferenceById(empresaId)).thenReturn(empresa);

        // Agir
        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Afirmar
        verify(empresaRepository, times(1)).atualizarTaxaDeposito(empresaId, valor);
    }

    // pode alterar taxaSaque de uma empresa existente
    @Test
    public void testMudarTaxaSaqueDeEmpresaExistente() {
        // Arranjo
        Long empresaId = 1L;
        String tipoTaxa = "SAQUE";
        double valor = 0.3;

        Empresa empresa = new Empresa();
        empresa.setId(empresaId);

        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(empresaRepository.getReferenceById(empresaId)).thenReturn(empresa);

        // Agir
        empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);

        // Afirmar
        verify(empresaRepository, times(1)).atualizarTaxaSaque(empresaId, valor);
    }

    // lança exceção ao tentar cadastrar empresa com parâmetros nulos
    @Test
    public void testExcecaoQuandoRegistrarEmpresaComParametrosNulos() {
        // Arranjo
        String cnpj = null;
        String nome = "Empresa Teste";
        Double saldo = 1000.0;
        double taxaDeposito = 0.1;
        double taxaSaque = 0.2;

        // Agir e Afirmar
        assertThrows(NullPointerException.class, () -> {
            empresaService.registrarEmpresa(cnpj, nome, saldo, taxaDeposito, taxaSaque);
        });
    }

    // lança exceção ao tentar alterar taxa de empresa inexistente
    @Test
    public void testExcecaoQuandoMudarTaxaDeEmpresaInexistente() {
        // Arranjo
        Long empresaId = 1L;
        String tipoTaxa = "DEPÓSITO";
        double valor = 0.2;

        when(empresaRepository.getReferenceById(empresaId)).thenReturn(null);

        // Agir e Afirmar
        assertThrows(EmpresaNaoEncontradaException.class, () -> {
            empresaService.mudarTaxaValorEmpresa(empresaId, tipoTaxa, valor);
        });
    }

    // Deveria criar a nova empresa e com todos os mesmos dados passados como parâmetro
    @Test
    void testRegistrarEmpresa() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);

        // Define o comportamento dos mocks
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation ->
        {
            Empresa savedEmpresa = invocation.getArgument(0);
            savedEmpresa.setId(1L);
            return savedEmpresa;
        });
        when(empresaRepository.findByCnpj(anyString())).thenReturn(Optional.of(empresa));
        when(empresaRepository.getReferenceById(anyLong())).thenReturn(empresa);

        // Chamada do método a ser testado
        empresaService.registrarEmpresa("04.252.011/0001-10", "Teste",
                5000.0, 0.5, 0.5);

        Empresa novaEmpresa = empresaRepository.findByCnpj(empresa.getCnpj()).orElse(null);

        Assertions.assertNotNull(novaEmpresa);
        Assertions.assertEquals(empresa.getNome(), novaEmpresa.getNome());
        Assertions.assertEquals(empresa.getCnpj(), novaEmpresa.getCnpj());
        Assertions.assertEquals(empresa.getTaxaDeposito(), novaEmpresa.getTaxaDeposito());
        Assertions.assertEquals(empresa.getTaxaSaque(), novaEmpresa.getTaxaSaque());
        Assertions.assertEquals(empresa.getSaldo(), novaEmpresa.getSaldo());
    }

    // Deveria listar todas as empresas
    @Test
    void testListarTodasEmpresas() {

        // Criação de objetos necessários
        Empresa empresa1 = new Empresa();
        empresa1.setNome("Teste1");
        empresa1.setCnpj("04.252.011/0001-10");
        empresa1.setTaxaDeposito(0.5);
        empresa1.setTaxaSaque(0.5);
        empresa1.setSaldo(5000.0);

        Empresa empresa2 = new Empresa();
        empresa2.setNome("Teste2");
        empresa2.setCnpj("84.867.778/0001-92");
        empresa2.setTaxaDeposito(0.6);
        empresa2.setTaxaSaque(0.6);
        empresa2.setSaldo(3000.0);

        Empresa empresa3 = new Empresa();
        empresa3.setNome("Teste3");
        empresa3.setCnpj("75.045.159/0001-21");
        empresa3.setTaxaDeposito(0.7);
        empresa3.setTaxaSaque(0.7);
        empresa3.setSaldo(8000.0);

        List<Empresa> empresas = new ArrayList<>();
        empresas.add(empresa1);
        empresas.add(empresa2);
        empresas.add(empresa3);

        // Define o comportamento dos mocks
        when(empresaRepository.findAll()).thenReturn(empresas);

        List<Empresa> result = empresaRepository.findAll();

        // Afirmações
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsAll(empresas));
    }

    // Deveria deletar uma empresa por seu id
    @Test
    void testDeleteEmpresa() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);

        // Define o comportamento dos mocks
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation ->
        {
            Empresa savedEmpresa = invocation.getArgument(0);
            savedEmpresa.setId(1L);
            return savedEmpresa;
        });
        when(empresaRepository.findByCnpj(anyString())).thenReturn(Optional.of(empresa));
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(empresaRepository.getReferenceById(anyLong())).thenReturn(empresa);
        doNothing().when(empresaRepository).delete(any(Empresa.class));

        // Chamada dos métodos a ser testado
        empresaService.registrarEmpresa("04.252.011/0001-10", "Teste",
                5000.0, 0.5, 0.5);

        Empresa novaEmpresa = empresaRepository.findByCnpj(empresa.getCnpj()).orElse(null);
        Assertions.assertNotNull(novaEmpresa);

        empresaService.deleteEmpresa(1L);

        verify(empresaRepository, times(1)).getReferenceById(1L);
        verify(empresaRepository, times(1)).delete(empresa);
    }
}