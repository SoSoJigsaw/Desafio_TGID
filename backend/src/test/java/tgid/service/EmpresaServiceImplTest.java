package tgid.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgid.entity.Empresa;
import tgid.repository.EmpresaRepository;
import tgid.repository.TransacaoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmpresaServiceImplTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmpresaServiceImpl empresaService;

    // Deveria criar a nova empresa e com todos os mesmos dados passados como parâmetro
    @Test
    void testRegistrarEmpresa() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresa.setSaldo(5000.0);

        empresaService.registrarEmpresa("04.252.011/0001-10", "Teste",
                5000.0, 0.5, 0.5);

        Empresa novaEmpresa = empresaRepository.findByCnpj(empresa.getCnpj()).orElse(null);

        Assertions.assertNotNull(novaEmpresa);
        Assertions.assertEquals(empresa.getNome(), novaEmpresa.getNome());
        Assertions.assertEquals(empresa.getCnpj(), novaEmpresa.getCnpj());
        Assertions.assertEquals(empresa.getTaxaDeposito(), novaEmpresa.getTaxaDeposito());
        Assertions.assertEquals(empresa.getTaxaSaque(), novaEmpresa.getTaxaSaque());
        Assertions.assertEquals(empresa.getSaldo(), novaEmpresa.getSaldo());

        empresaService.deleteEmpresa(novaEmpresa.getId());
    }

    // Deveria listar todas as empresas
    @Test
    void testListarTodasEmpresas() {

        List<Empresa> empresas = empresaRepository.findAll();

        Assertions.assertNotNull(empresas);

        for (Empresa empresa : empresas) {
            Assertions.assertTrue(empresaRepository.findById(empresa.getId()).isPresent());
        }
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

        empresaService.registrarEmpresa("04.252.011/0001-10", "Teste",
                5000.0, 0.5, 0.5);

        Empresa novaEmpresa = empresaRepository.findByCnpj(empresa.getCnpj()).orElse(null);

        empresaService.deleteEmpresa(novaEmpresa.getId());

        Empresa empresaDeletada = empresaRepository.findById(novaEmpresa.getId()).orElse(null);

        Assertions.assertNull(empresaDeletada);
    }
}