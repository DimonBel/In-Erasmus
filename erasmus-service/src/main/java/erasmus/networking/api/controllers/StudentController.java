package erasmus.networking.api.controllers;

import erasmus.networking.api.controllers.specs.StudentControllerSpecs;
import erasmus.networking.api.requests.student.GraduateRequest;
import erasmus.networking.api.responses.student.StudentDetailedResponse;
import erasmus.networking.domain.services.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = StudentController.BASE_PATH)
public class StudentController implements StudentControllerSpecs {

    private final StudentService studentService;

    protected static final String BASE_PATH = "/students";

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public ResponseEntity<List<StudentDetailedResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @Override
    public ResponseEntity<Void> graduateStudent(@Valid @RequestBody GraduateRequest graduateRequest) {
        studentService.graduateStudentFromUniversity(graduateRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<StudentDetailedResponse>> displayEnrolledStudents(
            @RequestParam String facultyAbbreviation) {
        log.info("Getting the list of students enrolled in university");
        List<StudentDetailedResponse> students =
                studentService.getStudentsEnrolledToFaculty(facultyAbbreviation);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<List<StudentDetailedResponse>> displayGraduatedStudents(
            @RequestParam String facultyAbbreviation) {
        log.info("Getting the list of students graduated from university");
        List<StudentDetailedResponse> students =
                studentService.getStudentsGraduatedFromFaculty(facultyAbbreviation);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<Page<StudentDetailedResponse>> searchStudents(
            @RequestParam(required = false) String search, Pageable pageable) {
        Page<StudentDetailedResponse> students = studentService.searchStudents(search, pageable);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<StudentDetailedResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }
}
