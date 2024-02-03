package tgid.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CPFValidatorTests {

    // Deve retornar verdadeiro para um CPF válido
    @Test
    public void test_valid_cpf() {
        CPFValidator cpfValidator = new CPFValidator();
        assertTrue(cpfValidator.isValid("12345678909", null));
    }

    // Deve retornar falso para um CPF inválido
    @Test
    public void test_invalid_cpf() {
        CPFValidator cpfValidator = new CPFValidator();
        assertFalse(cpfValidator.isValid("12345678900", null));
    }

    // Deve retornar falso para um CPF com menos de 11 dígitos
    @Test
    public void test_less_than_11_digits() {
        CPFValidator cpfValidator = new CPFValidator();
        assertFalse(cpfValidator.isValid("1234567890", null));
    }

    // Deve retornar falso para um CPF com todos os dígitos iguais
    @Test
    public void test_all_digits_equal() {
        CPFValidator cpfValidator = new CPFValidator();
        assertFalse(cpfValidator.isValid("11111111111", null));
    }

    // Deve retornar falso para um CPF com o primeiro dígito de verificação inválido
    @Test
    public void test_invalid_first_verification_digit() {
        CPFValidator cpfValidator = new CPFValidator();
        assertFalse(cpfValidator.isValid("12345678902", null));
    }

    // Deve retornar falso para um CPF com o segundo dígito de verificação inválido
    @Test
    public void test_invalid_second_verification_digit() {
        CPFValidator cpfValidator = new CPFValidator();
        assertFalse(cpfValidator.isValid("12345678903", null));
    }

}
