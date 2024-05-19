package erasmus.networking.api.controllers.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "University Operations", description = "Operations related to universities")
public interface UniversityControllerSpecs {

  @PostMapping
  @Operation(summary = "Create a new university", description = "Create a new university")
  @ApiResponse(responseCode = "201", description = "University created successfully")
  ResponseEntity<UniversityResponse> createUniversity(
      @Valid @RequestBody UniversityCreateRequest universityCreateRequest);

  @GetMapping
  @Operation(summary = "Get all universities", description = "Get all universities")
  @ApiResponse(responseCode = "200", description = "Universities retrieved successfully")
  ResponseEntity<Page<UniversityResponse>> getAllUniversities(Pageable pageable);

  @GetMapping("{id}")
  @Operation(summary = "Get a university by id", description = "Get a university by id")
  @ApiResponse(responseCode = "200", description = "University retrieved successfully")
  ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long id);

  @PutMapping("{id}")
  @Operation(summary = "Update a university", description = "Update a university")
  @ApiResponse(responseCode = "204", description = "University updated successfully")
  ResponseEntity<Void> updateUniversity(
      @PathVariable Long id, @Valid @RequestBody UniversityCreateRequest universityCreateRequest);

  @DeleteMapping("{id}")
  @Operation(summary = "Delete a university", description = "Delete a university")
  @ApiResponse(responseCode = "204", description = "University deleted successfully")
  ResponseEntity<Void> deleteUniversity(@PathVariable Long id);
}
