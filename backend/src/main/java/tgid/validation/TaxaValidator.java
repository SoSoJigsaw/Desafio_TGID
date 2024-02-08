package tgid.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
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
