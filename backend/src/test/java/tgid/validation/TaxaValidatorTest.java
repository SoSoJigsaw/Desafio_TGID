package tgid.validation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxaValidatorTest {

    @InjectMocks
    TaxaValidator taxaValidator;
    
    // Testa se uma entrada válida de "SAQUE" retorna verdadeiro
    @Test
    public void testEntradaValidaRetornaTrue() {
        boolean result = taxaValidator.isValid("SAQUE", null);
        assertTrue(result);
    }

    // Testa se uma entrada inválida de "TRANSFERÊNCIA" retorna falso
    @Test
    public void testEntradaInvalidaRetornaFalse() {
        boolean result = taxaValidator.isValid("TRANSFERÊNCIA", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "depósito" (minúsculo) retorna falso
    @Test
    public void testEntradaDepositoMinusculoRetornaFalse() {
        boolean result = taxaValidator.isValid("depósito", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "Saque" (capitalizada) retorna falso
    @Test
    public void testEntradaSaqueCapitalizadaRetornaFalse() {
        boolean result = taxaValidator.isValid("Saque", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "depósito " (com espaço final) retorna falso
    @Test
    public void testEntradaDepositoComEspacoFinalRetornaFalse() {
        boolean result = taxaValidator.isValid("depósito ", null);
        assertFalse(result);
    }

}
