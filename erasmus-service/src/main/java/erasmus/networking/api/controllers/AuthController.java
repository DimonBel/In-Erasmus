package erasmus.networking.api.controllers;

import erasmus.networking.api.controllers.specs.AuthControllerSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import erasmus.networking.api.controllers.specs.AuthControllerSpecs;
import erasmus.networking.api.requests.jwt.AuthenticationDTO;
import erasmus.networking.api.requests.student.StudentRequest;
import erasmus.networking.api.responses.jwt.JWT;
import erasmus.networking.api.responses.student.StudentWithJwtResponse;
import erasmus.networking.domain.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AuthController.BASE_PATH)
public class AuthController implements AuthControllerSpecs {
  protected static final String BASE_PATH = "/auth";

  private final AuthService authService;

  @Override
  public ResponseEntity<StudentWithJwtResponse> register(@Valid @RequestBody StudentRequest studentRequest) {
    StudentWithJwtResponse studentDetailedResponse = authService.registerUser(studentRequest);
    return ResponseEntity.status(201).body(studentDetailedResponse);
  }

  @Override
  public ResponseEntity<JWT> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
    JWT response =
        authService.authenticate(authenticationDTO.getEmail(), authenticationDTO.getPassword());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
