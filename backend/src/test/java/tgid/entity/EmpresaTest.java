package tgid.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class EmpresaTest {

    // Pode criar uma instância de 'Empresa' com atributos válidos
    @Test
    public void test_criar_instancia_com_atributos_validos() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertEquals("04.252.011/0001-10", empresa.getCnpj());
        assertEquals("Test Empresa", empresa.getNome());
        assertEquals(1000.0, empresa.getSaldo(), 0.001);
        assertEquals(0.1, empresa.getTaxaDeposito(), 0.001);
        assertEquals(0.2, empresa.getTaxaSaque(), 0.001);
    }

    // Pode definir e obter atributos de 'Empresa'
    @Test
    public void test_definir_e_obter_atributos() {
        Empresa empresa = new Empresa();
        empresa.setCnpj("04.252.011/0001-10");
        empresa.setNome("Test Empresa");
        empresa.setSaldo(1000.0);
        empresa.setTaxaDeposito(0.1);
        empresa.setTaxaSaque(0.2);

        assertEquals("04.252.011/0001-10", empresa.getCnpj());
        assertEquals("Test Empresa", empresa.getNome());
        assertEquals(1000.0, empresa.getSaldo(), 0.001);
        assertEquals(0.1, empresa.getTaxaDeposito(), 0.001);
        assertEquals(0.2, empresa.getTaxaSaque(), 0.001);
    }
}
