package tgid.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CNPJValidatorTest {

    @InjectMocks
    CNPJValidator cnpjValidator;

    // Deve retornar Verdadeiro para um CNPJ válido
    @Test
    public void testCnpjValido() {
        assertTrue(cnpjValidator.isValid("12345678000190", null));
    }

    // Deve retornar Verdadeiro para um CNPJ válido com caracteres especiais
    @Test
    public void testCnpjValidoComCaracteresEspeciais() {
        assertTrue(cnpjValidator.isValid("12.345.678/0001-90", null));
    }

    // Deve retornar Falso para um CNPJ vazio
    @Test
    public void testCnpjVazio() {
        assertFalse(cnpjValidator.isValid("", null));
    }

    // Deve retornar Falso para um CNPJ com menos de 14 dígitos
    @Test
    public void testCnpjComMenosDigitosQueNecessario() {
        assertFalse(cnpjValidator.isValid("1234567890123", null));
    }

    // Deve retornar Falso para um CNPJ com mais de 18 dígitos
    @Test
    public void testCnpjComMaisDigitosQueNecessario() {
        assertFalse(cnpjValidator.isValid("84.867.778/0001-923", null));
    }

}
