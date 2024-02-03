package tgid.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CNPJValidatorTests {

    // Deve retornar Verdadeiro para um CNPJ válido
    @Test
    public void test_valid_cnpj() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertTrue(cnpjValidator.isValid("12345678000190", null));
    }

    // Deve retornar Falso para um CNPJ inválido
    @Test
    public void test_invalid_cnpj() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertFalse(cnpjValidator.isValid("12345678901234", null));
    }

    // Deve retornar Verdadeiro para um CNPJ válido com caracteres especiais
    @Test
    public void test_valid_cnpj_with_special_characters() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertTrue(cnpjValidator.isValid("12.345.678/0001-90", null));
    }

    // Deve retornar Falso para um CNPJ nulo
    @Test
    public void test_null_cnpj() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertFalse(cnpjValidator.isValid(null, null));
    }

    // Deve retornar Falso para um CNPJ vazio
    @Test
    public void test_empty_cnpj() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertFalse(cnpjValidator.isValid("", null));
    }

    // Deve retornar Falso para um CNPJ com menos de 14 dígitos
    @Test
    public void test_short_cnpj() {
        CNPJValidator cnpjValidator = new CNPJValidator();
        assertFalse(cnpjValidator.isValid("1234567890123", null));
    }

}
