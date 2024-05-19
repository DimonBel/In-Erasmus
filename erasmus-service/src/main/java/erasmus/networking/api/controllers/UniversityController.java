package erasmus.networking.api.controllers;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import erasmus.networking.api.controllers.specs.UniversityControllerSpecs;
import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;
import erasmus.networking.domain.services.UniversityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = UniversityController.BASE_PATH)
@RequiredArgsConstructor
public class UniversityController implements UniversityControllerSpecs {

  private final UniversityService universityService;

  protected static final String BASE_PATH = "/universities";

  @Override
  public ResponseEntity<UniversityResponse> createUniversity(
      @Valid @RequestBody UniversityCreateRequest universityCreateRequest) {
    UniversityResponse universityResponse =
        universityService.createUniversity(universityCreateRequest);
    URI location = URI.create(String.format("/universities/%d", universityResponse.id()));
    return ResponseEntity.created(location).body(universityResponse);
  }

  @Override
  public ResponseEntity<Page<UniversityResponse>> getAllUniversities(Pageable pageable) {
    Page<UniversityResponse> universityResponses = universityService.getAllUniversities(pageable);
    return ResponseEntity.ok(universityResponses);
  }

  @Override
  public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long id) {
    UniversityResponse universityResponse = universityService.getUniversityById(id);
    return ResponseEntity.ok(universityResponse);
  }

  @Override
  public ResponseEntity<Void> updateUniversity(
      @PathVariable Long id, @Valid @RequestBody UniversityCreateRequest universityCreateRequest) {
    universityService.updateUniversity(id, universityCreateRequest);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
    universityService.deleteUniversity(id);
    return ResponseEntity.noContent().build();
  }
}
