package tgid.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class EmpresaTests {

    // Pode criar uma instância de 'Empresa' com atributos válidos
    @Test
    public void test_criar_instancia_com_atributos_validos() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertEquals("valid_cnpj", empresa.getCnpj());
        assertEquals("Test Empresa", empresa.getNome());
        assertEquals(1000.0, empresa.getSaldo(), 0.001);
        assertEquals(0.1, empresa.getTaxaDeposito(), 0.001);
        assertEquals(0.2, empresa.getTaxaSaque(), 0.001);
    }

    // Pode definir e obter atributos de 'Empresa'
    @Test
    public void test_definir_e_obter_atributos() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertEquals("valid_cnpj", empresa.getCnpj());
        assertEquals("Test Empresa", empresa.getNome());
        assertEquals(1000.0, empresa.getSaldo(), 0.001);
        assertEquals(0.1, empresa.getTaxaDeposito(), 0.001);
        assertEquals(0.2, empresa.getTaxaSaque(), 0.001);
    }

    // Pode salvar uma instância de 'Empresa' no banco de dados
    @Test
    public void test_salvar_no_banco_de_dados() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        entityManager.getTransaction().begin();
        entityManager.persist(empresa);
        entityManager.getTransaction().commit();

        Empresa savedEmpresa = entityManager.find(Empresa.class, empresa.getId());

        assertEquals("valid_cnpj", savedEmpresa.getCnpj());
        assertEquals("Test Empresa", savedEmpresa.getNome());
        assertEquals(1000.0, savedEmpresa.getSaldo(), 0.001);
        assertEquals(0.1, savedEmpresa.getTaxaDeposito(), 0.001);
        assertEquals(0.2, savedEmpresa.getTaxaSaque(), 0.001);

        entityManager.getTransaction().begin();
        entityManager.remove(savedEmpresa);
        entityManager.getTransaction().commit();
    }

    // Lança exceção ao criar uma instância de 'Empresa' com 'cnpj' em branco
    @Test
    public void test_lanca_excecao_com_cnpj_em_branco() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertThrows(ConstraintViolationException.class, () -> {
            EntityManager entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        });
    }

    // Lança exceção ao criar uma instância de 'Empresa' com 'nome' em branco
    @Test
    public void test_lanca_excecao_com_nome_em_branco() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertThrows(ConstraintViolationException.class, () -> {
            EntityManager entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        });
    }

    // Lança exceção ao criar uma instância de 'Empresa' com 'saldo' negativo
    @Test
    public void test_lanca_excecao_com_saldo_negativo() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("valid_cnpj");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(-1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertThrows(ConstraintViolationException.class, () -> {
            EntityManager entityManager = Persistence.createEntityManagerFactory("test").createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        });
    }
}
