package erasmus.networking.api.controllers.specs;

import erasmus.networking.common.utils.ApiDescriptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;
import erasmus.networking.api.requests.faculty.FacultyRequest;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.faculty.FacultyWithStudentsResponse;
import erasmus.networking.api.responses.faculty.IsBelongToFacultyResponse;
import erasmus.networking.common.utils.ApiDescriptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Faculties Operations", description = "Operations related to faculties")
public interface FacultyControllerSpecs {

  @PostMapping
  @Operation(
      summary = ApiDescriptions.SUMMARY_CREATE_FACULTY,
      description = ApiDescriptions.DESC_CREATE_FACULTY)
  @ApiResponse(
      responseCode = "201",
      description = "Faculty created successfully",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FacultyResponse.class)))
  @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
  ResponseEntity<FacultyResponse> createFaculty(@Valid @RequestBody FacultyRequest facultyRequest);

  @GetMapping("/searchStudentBelongingToFaculty")
  @Operation(
      summary = ApiDescriptions.SUMMARY_SEARCH_STUDENT_BELONGING_TO_FACULTY,
      description = ApiDescriptions.DESC_SEARCH_STUDENT_BELONGING_TO_FACULTY)
  @ApiResponse(
      responseCode = "200",
      description = "Faculty found",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FacultyWithStudentsResponse.class)))
  @ApiResponse(responseCode = "404", description = "Student does not belong to any faculty")
  ResponseEntity<FacultyWithStudentsResponse> searchStudentBelongingToFaculty(
      @Email @RequestParam String studentEmail);

  @GetMapping("/isStudentBelongsToFaculty")
  @Operation(
      summary = ApiDescriptions.SUMMARY_IS_STUDENT_BELONGS_TO_FACULTY,
      description = ApiDescriptions.DESC_IS_STUDENT_BELONGS_TO_FACULTY)
  @ApiResponse(
      responseCode = "200",
      description = "Check result provided",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = IsBelongToFacultyResponse.class)))
  @ApiResponse(responseCode = "404", description = "Student or faculty not found")
  ResponseEntity<IsBelongToFacultyResponse> isStudentBelongsToFaculty(
      @RequestParam String facultyAbbreviation, @Email @RequestParam String studentEmail);

  @GetMapping("/display")
  @Operation(
      summary = ApiDescriptions.SUMMARY_DISPLAY_FACULTIES,
      description = ApiDescriptions.DESC_DISPLAY_FACULTIES)
  @ApiResponse(
      responseCode = "200",
      description = "Paged list of faculties",
      content =
          @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
  ResponseEntity<Page<FacultyWithStudentsResponse>> displayFaculties(
      @PageableDefault(
              sort = {"facultyName"},
              direction = Sort.Direction.ASC)
          Pageable pageable);

  @GetMapping("/displayFaculty")
  @Operation(
      summary = ApiDescriptions.SUMMARY_DISPLAY_FACULTIES_BY_FIELD,
      description = ApiDescriptions.DESC_DISPLAY_FACULTIES_BY_FIELD)
  @ApiResponse(
      responseCode = "200",
      description = "Faculties displayed",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FacultyResponse.class)))
  ResponseEntity<List<FacultyResponse>> displayFaculty(@RequestParam String studyField);

  @GetMapping("{id}")
  @Operation(
      summary = ApiDescriptions.SUMMARY_GET_FACULTIES_BY_ID,
      description = ApiDescriptions.DESC_GET_FACULTIES_BY_ID)
  @ApiResponse(
      responseCode = "200",
      description = "Faculty retrieved successfully",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FacultyResponse.class)))
  ResponseEntity<FacultyResponse> getFacultyById(@PathVariable Long id);

  @GetMapping("/search")
  @Operation(
      summary = ApiDescriptions.SUMMARY_SEARCH_FACULTIES,
      description =
          """
                      Example query:
                      http://localhost:8080/faculty/search?name=Test%20Faculty&abbreviation=TF&sort=name,asc&sort=createdAt,desc&page=0&size=10
                    """)
  @ApiResponse(
      responseCode = "200",
      description = "Faculties retrieved successfully",
      content =
          @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
  ResponseEntity<Page<FacultyResponse>> searchFaculties(
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
          Pageable pageable);

  @PutMapping("{id}")
  @Operation(
      summary = ApiDescriptions.SUMMARY_UPDATE_FACULTY,
      description = ApiDescriptions.DESC_UPDATE_FACULTY)
  @ApiResponse(responseCode = "204", description = "Faculty updated successfully")
  @ApiResponse(responseCode = "400", description = "Invalid input, object invalid")
  @ApiResponse(responseCode = "404", description = "Faculty not found")
  ResponseEntity<Void> updateFaculty(
      @PathVariable("id") Long id, @Valid @RequestBody FacultyRequest facultyRequest);

  @DeleteMapping("{id}")
  @Operation(
      summary = ApiDescriptions.SUMMARY_DELETE_FACULTY,
      description = ApiDescriptions.DESC_DELETE_FACULTY)
  @ApiResponse(responseCode = "204", description = "Faculty deleted successfully")
  @ApiResponse(responseCode = "404", description = "Faculty not found")
  ResponseEntity<Void> deleteFaculty(@PathVariable("id") Long id);
}
