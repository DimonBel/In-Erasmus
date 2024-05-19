package erasmus.networking.api.requests.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import erasmus.networking.common.exceptions.valiation.ValidStudyField;

public record FacultyRequest(
    @NotNull(message = "University ID is required") Long universityId,
    @NotBlank(message = "Faculty name is required")
        @Size(min = 3, max = 50, message = "Faculty name must be between 3 and 50 characters")
        String facultyName,
    @NotBlank(message = "Abbreviation is required")
        @Size(min = 2, max = 10, message = "Abbreviation must be between 2 and 10 characters")
        String abbreviation,
    @NotEmpty(message = "Study field is required")
        @Size(min = 3, max = 50, message = "Study field must be between 3 and 50 characters")
        @ValidStudyField
        String studyField) {}
