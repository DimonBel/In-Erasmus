package erasmus.networking.api.controllers;

import erasmus.networking.api.requests.FeedbackRequest;
import erasmus.networking.api.responses.FeedbackResponse;
import erasmus.networking.api.responses.ProgramResponse;
import erasmus.networking.domain.services.FeedbackService;
import erasmus.networking.domain.services.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
@Tag(name = "Program Operations", description = "Operations related to programs")
public class ProgramController {

    private final ProgramService programService;

    private final FeedbackService feedbackService;

    @GetMapping("/{studentId}")
    @Operation(
            summary = "Get programs by student ID",
            description = "Retrieves all programs associated with a student based on their ID")
    @ApiResponse(responseCode = "200", description = "Programs retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProgramResponse.class)))
    public ResponseEntity<ProgramResponse> getProgramsAssociatedToStudents(@PathVariable Long studentId) {
        ProgramResponse programResponse = programService.getProgramsByStudentId(studentId);
        return ResponseEntity.ok(programResponse);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all programs",
            description = "Retrieves all programs")
    @ApiResponse(responseCode = "200", description = "Programs retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProgramResponse.class)))
    public ResponseEntity<List<ProgramResponse>> getAllPrograms() {
        List<ProgramResponse> programResponse = programService.getAllPrograms();
        return ResponseEntity.ok(programResponse);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search programs",
            security = @SecurityRequirement(name = "bearerAuth"),
            description =
                    """
                            Retrieves a list of programs based on the search query.
                            -name:
                            -description:
                            -abbreviation:
                            -link
                            -study_field:
                            -created_at:
                            -trip_date:
                            -application_deadline:
                                                
                            Example Query:
                            `http://localhost:8080/programs/search?q=name:erasmus,application_deadline>2024-05-01,`
                            """)
    @ApiResponse(
            responseCode = "200",
            description = "Paged list of students",
            content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public ResponseEntity<List<ProgramResponse>> searchPrograms(@RequestParam(required = false) String q, Pageable pageable) {
        List<ProgramResponse> programResponse = programService.searchPrograms(q, pageable);
        return ResponseEntity.ok(programResponse);
    }

    @GetMapping("/feedbacks")
    @Operation(
            summary = "Get all feedbacks",
            description = "Retrieves all feedbacks")
    @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResponse.class)))
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        List<FeedbackResponse> programResponse = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(programResponse);
    }

    @GetMapping("/feedbacks/{userId}")
    @Operation(
            summary = "Get feedbacks by user ID",
            description = "Retrieves all feedbacks associated with a user based on their ID")
    @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResponse.class)))
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByUserId(@PathVariable Long userId) {
        List<FeedbackResponse> programResponse = feedbackService.getFeedbacksByStudentId(userId);
        return ResponseEntity.ok(programResponse);
    }

    @GetMapping("/feedbacks/{feedbackId}")
    @Operation(
            summary = "Get feedback by feedback ID",
            description = "Retrieves a feedback based on its ID")
    @ApiResponse(responseCode = "200", description = "Feedback retrieved successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResponse.class)))
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackResponse programResponse = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(programResponse);
    }

    @PostMapping("/feedbacks/{feedbackId}/like")
    @Operation(
            summary = "Like a feedback",
            description = "Likes a feedback based on its ID")
    @ApiResponse(responseCode = "200", description = "Feedback liked successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResponse.class)))
    public ResponseEntity<FeedbackResponse> likeFeedback(@PathVariable Long feedbackId) {
        FeedbackResponse programResponse = feedbackService.likeFeedback(feedbackId);
        return ResponseEntity.ok(programResponse);
    }

    @PostMapping("/feedbacks/create")
    @Operation(
            summary = "Create a feedback",
            description = "Creates a feedback")
@ApiResponse(responseCode = "200", description = "Feedback created successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FeedbackResponse.class)))
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
       return null;
    }



}
