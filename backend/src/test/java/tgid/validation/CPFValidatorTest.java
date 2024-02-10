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
public class CPFValidatorTest {

    @InjectMocks
    CPFValidator cpfValidator;

    // Deve retornar verdadeiro para um CPF válido
    @Test
    public void test_valid_cpf() {
        assertTrue(cpfValidator.isValid("12345678909", null));
    }

    // Deve retornar falso para um CPF inválido
    @Test
    public void testCpfInvalido() {
        assertFalse(cpfValidator.isValid("12345678900", null));
    }

    // Deve retornar falso para um CPF com menos de 11 dígitos
    @Test
    public void testCpfComMenosDigitosQueNecessario() {
        assertFalse(cpfValidator.isValid("1234567890", null));
    }

    // Deve retornar falso para um CPF com mais de 14 dígitos
    @Test
    public void testCpfComMaisDigitosQueNecessario() {
        assertFalse(cpfValidator.isValid("123.456.789-093", null));
    }

    // Deve retornar falso para um CPF com o primeiro dígito de verificação inválido
    @Test
    public void testCpfCpmPrimeiroDigitoVerificacaoInvalido() {
        assertFalse(cpfValidator.isValid("12345678902", null));
    }

    // Deve retornar falso para um CPF com o segundo dígito de verificação inválido
    @Test
    public void testCpfCpmSegundoDigitoVerificacaoInvalido() {
        assertFalse(cpfValidator.isValid("12345678903", null));
    }

}
