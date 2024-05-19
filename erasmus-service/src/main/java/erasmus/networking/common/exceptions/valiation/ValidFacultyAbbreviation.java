package erasmus.networking.common.exceptions.valiation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FacultyAbbreviationValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFacultyAbbreviation {
    String message() default "Invalid Faculty Abbreviation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
