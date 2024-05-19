package erasmus.networking.common.exceptions.valiation;

import erasmus.networking.domain.services.FacultyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import erasmus.networking.domain.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudyFieldValidator implements ConstraintValidator<ValidStudyField, String> {

  @Autowired private FacultyService facultyService;

  @Override
  public void initialize(ValidStudyField constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && facultyService.isValidStudyField(value);
  }
}
