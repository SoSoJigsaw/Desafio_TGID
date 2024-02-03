package tgid.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaxaValidatorTests {

    // Testa se uma entrada válida de "SAQUE" retorna verdadeiro
    @Test
    public void test_valid_input_saque_returns_true() {
        TaxaValidator validator = new TaxaValidator();
        boolean result = validator.isValid("SAQUE", null);
        assertTrue(result);
    }

    // Testa se uma entrada inválida de "TRANSFERÊNCIA" retorna falso
    @Test
    public void test_invalid_input_transferencia_returns_false() {
        TaxaValidator validator = new TaxaValidator();
        boolean result = validator.isValid("TRANSFERÊNCIA", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "depósito" (minúsculo) retorna falso
    @Test
    public void test_input_deposito_lowercase_returns_false() {
        TaxaValidator validator = new TaxaValidator();
        boolean result = validator.isValid("depósito", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "Saque" (maiúsculo) retorna falso
    @Test
    public void test_input_saque_capitalized_returns_false() {
        TaxaValidator validator = new TaxaValidator();
        boolean result = validator.isValid("Saque", null);
        assertFalse(result);
    }

    // Testa se uma entrada de "depósito " (com espaço final) retorna falso
    @Test
    public void test_input_deposito_with_trailing_space_returns_false() {
        TaxaValidator validator = new TaxaValidator();
        boolean result = validator.isValid("depósito ", null);
        assertFalse(result);
    }

}
