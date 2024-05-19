package erasmus.networking.api.controllers;

import erasmus.networking.api.controllers.specs.FacultyControllerSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import erasmus.networking.api.controllers.specs.FacultyControllerSpecs;
import erasmus.networking.api.requests.faculty.FacultyRequest;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.faculty.FacultyWithStudentsResponse;
import erasmus.networking.api.responses.faculty.IsBelongToFacultyResponse;
import erasmus.networking.domain.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = FacultyController.BASE_PATH)
public class FacultyController implements FacultyControllerSpecs {

  private final FacultyService facultyService;

  protected static final String BASE_PATH = "/faculty";

  @Autowired
  public FacultyController(FacultyService facultyService) {
    this.facultyService = facultyService;
  }

  @Override
  public ResponseEntity<FacultyResponse> createFaculty(
      @Valid @RequestBody FacultyRequest facultyRequest) {
    FacultyResponse facultyResponse = facultyService.saveFaculty(facultyRequest);
    return ResponseEntity.status(201).body(facultyResponse);
  }

  @Override
  public ResponseEntity<FacultyWithStudentsResponse> searchStudentBelongingToFaculty(
      @Email @RequestParam String studentEmail) {
    FacultyWithStudentsResponse faculty = facultyService.getStudentBelongingToFaculty(studentEmail);
    return ResponseEntity.ok(faculty);
  }

  @Override
  public ResponseEntity<IsBelongToFacultyResponse> isStudentBelongsToFaculty(
      @RequestParam String facultyAbbreviation, @Email @RequestParam String studentEmail) {
    IsBelongToFacultyResponse answer =
        facultyService.isBelongToFacultyResponse(studentEmail, facultyAbbreviation);
    return ResponseEntity.ok(answer);
  }

  @Override
  public ResponseEntity<Page<FacultyWithStudentsResponse>> displayFaculties(
      @PageableDefault(
              sort = {"facultyName"},
              direction = Sort.Direction.ASC)
          Pageable pageable) {
    Page<FacultyWithStudentsResponse> faculties =
        facultyService.getAllFacultiesWithStudents(pageable);
    return ResponseEntity.ok(faculties);
  }

  @Override
  public ResponseEntity<List<FacultyResponse>> displayFaculty(@RequestParam String studyField) {
    var facultiesFiltered = facultyService.getAllFacultiesByField(studyField);
    return ResponseEntity.ok(facultiesFiltered);
  }

  @Override
  public ResponseEntity<FacultyResponse> getFacultyById(@PathVariable Long id) {
    FacultyResponse facultyResponse = facultyService.getFacultyById(id);
    return ResponseEntity.ok(facultyResponse);
  }

  @Override
  public ResponseEntity<Page<FacultyResponse>> searchFaculties(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String abbreviation,
      @RequestParam(required = false) Long universityId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime createdAfter,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime createdBefore,
      @PageableDefault(
              sort = {"facultyName", "createdAt"},
              direction = Sort.Direction.ASC)
          Pageable pageable) {

    Page<FacultyResponse> faculties =
        facultyService.searchFaculties(
            name,
            abbreviation,
            universityId,
            createdAfter != null ? createdAfter.toInstant(ZoneOffset.UTC) : null,
            createdBefore != null ? createdBefore.toInstant(ZoneOffset.UTC) : null,
            pageable);

    return ResponseEntity.ok(faculties);
  }

  @Override
  public ResponseEntity<Void> updateFaculty(
      @PathVariable("id") Long id, @Valid @RequestBody FacultyRequest facultyRequest) {
    facultyService.updateFaculty(id, facultyRequest);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteFaculty(@PathVariable("id") Long id) {
    facultyService.deleteFaculty(id);
    return ResponseEntity.noContent().build();
  }
}
