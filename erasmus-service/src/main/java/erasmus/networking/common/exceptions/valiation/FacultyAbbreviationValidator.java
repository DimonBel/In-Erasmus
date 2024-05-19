package erasmus.networking.common.exceptions.valiation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FacultyAbbreviationValidator
    implements ConstraintValidator<ValidFacultyAbbreviation, String> {

  @Override
  public void initialize(ValidFacultyAbbreviation constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null;
  }
}
