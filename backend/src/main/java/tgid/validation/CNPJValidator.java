package tgid.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tgid.exception.CnpjInvalidoException;

@Slf4j
@Component
public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public void initialize(CNPJ constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {

        try {
            // Remove pontos, traços e barras do CNPJ
            String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");

            // Verifica se o CNPJ tem 14 dígitos
            if (cnpjLimpo.length() != 14) {
                return false;
            }

            // Aplica o algoritmo de validação de CNPJ
            int[] multiplicadores = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;

            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpjLimpo.charAt(i)) * multiplicadores[i];
            }

            int resto = soma % 11;
            int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

            if (Character.getNumericValue(cnpjLimpo.charAt(12)) != digitoVerificador1) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpjLimpo.charAt(i)) * multiplicadores[i];
            }

            resto = soma % 11;
            int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

            // Verifica se os dígitos verificadores estão corretos
            return Character.getNumericValue(cnpjLimpo.charAt(12)) == digitoVerificador2;

        } catch (Exception e) {
            throw new CnpjInvalidoException(e);
        }

    }

}
