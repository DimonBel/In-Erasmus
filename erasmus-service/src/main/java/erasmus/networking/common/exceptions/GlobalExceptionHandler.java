package erasmus.networking.common.exceptions;

import java.net.URI;
import java.util.List;

import erasmus.networking.common.exceptions.faculties.FacultyAlreadyExistsException;
import erasmus.networking.common.exceptions.faculties.FacultyNotFoundException;
import erasmus.networking.common.exceptions.faculties.InvalidFacultyRequestException;
import erasmus.networking.common.exceptions.students.FacultyAbbreviationNotFoundException;
import erasmus.networking.common.exceptions.students.StudentAlreadyExistsException;
import erasmus.networking.common.exceptions.students.StudentNotFoundException;
import erasmus.networking.common.exceptions.errorDetails.Error;
import erasmus.networking.common.exceptions.errorDetails.ErrorDetails;
import erasmus.networking.common.exceptions.faculties.FacultyAlreadyExistsException;
import erasmus.networking.common.exceptions.faculties.FacultyNotFoundException;
import erasmus.networking.common.exceptions.faculties.InvalidFacultyRequestException;
import erasmus.networking.common.exceptions.students.FacultyAbbreviationNotFoundException;
import erasmus.networking.common.exceptions.students.StudentAlreadyExistsException;
import erasmus.networking.common.exceptions.students.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDetails> handleValidationException(
      MethodArgumentNotValidException ex) {
    List<Error> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(error -> new Error(((FieldError) error).getField(), error.getDefaultMessage()))
            .toList();

    ErrorDetails errorDetails =
        buildErrorDetails(
            "Validation Error",
            HttpStatus.BAD_REQUEST,
            "One or more validation errors occurred",
            errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex, WebRequest request) {
    ErrorDetails errorDetails =
        buildErrorDetails(
            ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
  }

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleStudentNotFoundException(StudentNotFoundException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("Student Not Found", HttpStatus.NOT_FOUND, ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
  }

  @ExceptionHandler(FacultyAlreadyExistsException.class)
  public ResponseEntity<ErrorDetails> handleFacultyAlreadyExistsException(
      FacultyAlreadyExistsException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("Faculty Already Exists", HttpStatus.BAD_REQUEST, ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler({FacultyAbbreviationNotFoundException.class, FacultyNotFoundException.class})
  public ResponseEntity<ErrorDetails> handleFacultyAbbreviationNotFoundException(
      RuntimeException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("Faculty Not Found", HttpStatus.NOT_FOUND, ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
  }

  @ExceptionHandler(InvalidFacultyRequestException.class)
  public ResponseEntity<ErrorDetails> handleFacultyAbbreviationNotFoundException(
      InvalidFacultyRequestException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("Invalid input data", HttpStatus.BAD_REQUEST, ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler(StudentAlreadyExistsException.class)
  public ResponseEntity<ErrorDetails> handleStudentAlreadyExistsException(
      StudentAlreadyExistsException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("Student Already Exists", HttpStatus.CONFLICT, ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(
      UsernameNotFoundException ex) {
    ErrorDetails errorDetails =
        buildErrorDetails("User Not Found", HttpStatus.NOT_FOUND, ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
  }

  private ErrorDetails buildErrorDetails(String title, HttpStatus status, String detail) {
    return new ErrorDetails(
        title,
        status.value(),
        detail,
        URI.create("https://localhost/docs/errors#" + status.value()),
        URI.create("/errors/" + System.currentTimeMillis()),
        new Error[0]);
  }

  private ErrorDetails buildErrorDetails(
      String title, HttpStatus status, String detail, List<Error> errors) {
    return new ErrorDetails(
        title,
        status.value(),
        detail,
        URI.create("https://localhost/docs/errors#" + status.value()),
        URI.create("/errors/" + System.currentTimeMillis()),
        errors.toArray(new Error[0]));
  }
}
