package tgid.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaxaValidator implements ConstraintValidator<Taxa, String> {

    @Override
    public void initialize(Taxa constraintAnnotation) {
    }

    @Override
    public boolean isValid(String taxa, ConstraintValidatorContext context) {

        return Objects.equals(taxa, "DEPÓSITO") || Objects.equals(taxa, "SAQUE");

    }

}
