package erasmus.networking.api.controllers.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import erasmus.networking.api.requests.student.GraduateRequest;
import erasmus.networking.api.responses.student.StudentDetailedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Operations", description = "Operations related to students")
public interface StudentControllerSpecs {

    @GetMapping("/get")
    @Operation(
            summary = "Get all students",
            description = "Retrieves a list of all registered students",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of students",
            content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    ResponseEntity<List<StudentDetailedResponse>> getAllStudents();

    @PatchMapping("/graduate")
    @Operation(
            summary = "Graduate a student",
            description = "Marks a student as graduated based on their email",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Student graduated successfully")
    @ApiResponse(responseCode = "404", description = "Student not found")
    ResponseEntity<Void> graduateStudent(@Valid @RequestBody GraduateRequest graduateRequest);

    @GetMapping("/getEnrolledStudents")
    @Operation(
            summary = "Display enrolled students",
            description = "Displays students currently enrolled in a specified faculty",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of enrolled students",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentDetailedResponse.class)))
    @ApiResponse(responseCode = "404", description = "No students enrolled")
    ResponseEntity<List<StudentDetailedResponse>> displayEnrolledStudents(@RequestParam String facultyAbbreviation);

    @GetMapping("/getGraduatedStudents")
    @Operation(
            summary = "Display graduated students",
            description = "Displays students who have graduated from a specified faculty",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "List of graduated students",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentDetailedResponse.class)))
    @ApiResponse(responseCode = "404", description = "No students graduated")
    ResponseEntity<List<StudentDetailedResponse>> displayGraduatedStudents(@RequestParam String facultyAbbreviation);

    @GetMapping("/search")
    @Operation(
            summary = "Search students",
            security = @SecurityRequirement(name = "bearerAuth"),
            description =
                    """
                    Search students based on the given criteria, including first name, last name, graduation status,
                    and other attributes with pagination and sorting.
        
                    Example Search Parameters:
                    - firstName:John
                    - lastName:Doe
                    - isGraduated:true
                    - enrollmentDate>2024-05-01
        
                    Example Query:
                    `http://localhost:8080/students/search?search=firstName:John,enrollmentDate>2024-05-01,`
                    """)
    @ApiResponse(
            responseCode = "200",
            description = "Paged list of students",
            content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    ResponseEntity<Page<StudentDetailedResponse>> searchStudents(@RequestParam(required = false) String search, Pageable pageable);

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete student",
            description = "Delete student based on id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Student deleted successfully")
    @ApiResponse(responseCode = "404", description = "Student not found")
    ResponseEntity<Void> deleteStudent(@PathVariable Long id);

    @GetMapping("{id}")
    @Operation(
            summary = "Get student",
            description = "Get student based on id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "Student retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentDetailedResponse.class)))
    @ApiResponse(responseCode = "404", description = "Student not found")
    ResponseEntity<StudentDetailedResponse> getStudent(@PathVariable Long id);
}
