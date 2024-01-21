package tgid.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaxaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Taxa {

    String message() default "Taxa inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
