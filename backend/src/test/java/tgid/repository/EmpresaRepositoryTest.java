package tgid.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tgid.entity.Empresa;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Transactional
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EntityManager entityManager;


    // Deveria atualizar a taxa de depósito da empresa com sucesso
    @Test
    public void testAtualizarTaxaDeposito() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setSaldo(5000.0);
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresaRepository.save(empresa);
        entityManager.flush();

        empresaRepository.atualizarTaxaDeposito(empresa.getId(), 0.6);
        entityManager.flush();
        entityManager.clear();

        Empresa empresaAtualizada = empresaRepository.findById(empresa.getId()).orElse(null);
        Assertions.assertNotNull(empresaAtualizada);
        Assertions.assertEquals(0.6, empresaAtualizada.getTaxaDeposito());

    }

    // Deveria atualizar a taxa de saque da empresa com sucesso
    @Test
    public void testAtualizarTaxaSaque() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setSaldo(5000.0);
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresaRepository.save(empresa);
        entityManager.flush();

        empresaRepository.atualizarTaxaSaque(empresa.getId(), 0.6);
        entityManager.flush();
        entityManager.clear();

        Empresa empresaAtualizada = empresaRepository.findById(empresa.getId()).orElse(null);
        Assertions.assertNotNull(empresaAtualizada);
        Assertions.assertEquals(0.6, empresaAtualizada.getTaxaSaque());

    }

    // Deveria atualizar o saldo da empresa com sucesso
    @Test
    public void testAtualizarSaldo() {

        Empresa empresa = new Empresa();
        empresa.setNome("Teste");
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setSaldo(5000.0);
        empresa.setTaxaDeposito(0.5);
        empresa.setTaxaSaque(0.5);
        empresaRepository.save(empresa);
        entityManager.flush();

        empresaRepository.atualizarSaldo(empresa.getId(), 4000.0);
        entityManager.flush();
        entityManager.clear();

        Empresa empresaAtualizada = empresaRepository.findById(empresa.getId()).orElse(null);
        Assertions.assertNotNull(empresaAtualizada);
        Assertions.assertEquals(4000.0, empresaAtualizada.getSaldo());

    }

}
