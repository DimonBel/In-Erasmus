package erasmus.networking.api.requests.student;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import erasmus.networking.common.exceptions.valiation.ValidFacultyAbbreviation;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class StudentRequest {
  @JsonProperty("facultyAbbreviation")
  @NotBlank(message = "Faculty abbreviation is required")
  @ValidFacultyAbbreviation
  private String facultyAbbreviation;

  @JsonProperty("firstName")
  @NotBlank(message = "First name is required")
  @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
  private String firstName;

  @JsonProperty("lastName")
  @NotBlank(message = "Last name is required")
  @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
  private String lastName;

  @JsonProperty("email")
  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @JsonProperty("password")
  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
  private String password;

  @JsonProperty("dateOfBirth")
  @NotBlank(message = "Date of birth is required")
  private String dateOfBirth;
}
