package erasmus.networking.api.controllers.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import erasmus.networking.api.requests.jwt.AuthenticationDTO;
import erasmus.networking.api.requests.student.StudentRequest;
import erasmus.networking.api.responses.jwt.JWT;
import erasmus.networking.api.responses.student.StudentWithJwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication Operations", description = "Operations related to authentication")
public interface AuthControllerSpecs {

    @PostMapping("/register")
    @Operation(
            summary = "Create a new user",
            description = "Registers a new user with the provided details")
    @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentWithJwtResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    ResponseEntity<StudentWithJwtResponse> register(@Valid @RequestBody StudentRequest studentRequest);

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a student",
            description = "Authenticates a student based on their email and password")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully authenticated",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = JWT.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    ResponseEntity<JWT> authenticate(@Valid @RequestBody AuthenticationDTO authenticationDTO);
}
