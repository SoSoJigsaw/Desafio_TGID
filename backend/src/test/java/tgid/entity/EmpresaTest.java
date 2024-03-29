package tgid.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaTest {

    // Pode criar uma instância de 'Empresa' com atributos válidos
    @Test
    public void testCriarInstanciaComAtributosValidos() {
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
    public void testDefinirObterAtributos() {
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
