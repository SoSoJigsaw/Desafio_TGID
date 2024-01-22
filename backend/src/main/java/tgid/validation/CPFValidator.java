package tgid.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        // Remove pontos e hífen do CPF
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpfLimpo.length() != 11) {
            return false;
        }

        // Aplica o algoritmo de validação de CPF (lógica dos dígitos verificadores / os dois últimos dígitos
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        // Verifica se os dígitos verificadores estão corretos
        return (digitoVerificador1 == Character.getNumericValue(cpfLimpo.charAt(9)))
                && (digitoVerificador2 == Character.getNumericValue(cpfLimpo.charAt(10)));

    }

}
