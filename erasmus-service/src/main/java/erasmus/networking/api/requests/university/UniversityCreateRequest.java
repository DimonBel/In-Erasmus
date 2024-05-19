package erasmus.networking.api.requests.university;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UniversityCreateRequest(
    @NotEmpty(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name) {}
