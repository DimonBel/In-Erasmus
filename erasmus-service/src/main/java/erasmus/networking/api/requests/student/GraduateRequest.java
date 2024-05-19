package erasmus.networking.api.requests.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraduateRequest {
  @JsonProperty("email")
  @NotEmpty(message = "Email is required")
  @Email
  private String email;
}
