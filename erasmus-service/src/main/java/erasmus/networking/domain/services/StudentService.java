package erasmus.networking.domain.services;

import erasmus.networking.api.requests.student.StudentRequest;
import erasmus.networking.api.responses.student.StudentDetailedResponse;
import erasmus.networking.common.enums.Role;
import erasmus.networking.common.exceptions.students.FacultyAbbreviationNotFoundException;
import erasmus.networking.common.exceptions.students.StudentAlreadyExistsException;
import erasmus.networking.common.exceptions.students.StudentNotFoundException;
import erasmus.networking.common.utils.ParseDate;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import erasmus.networking.api.requests.student.StudentRequest;
import erasmus.networking.api.responses.student.StudentDetailedResponse;
import erasmus.networking.common.enums.Role;
import erasmus.networking.common.exceptions.students.FacultyAbbreviationNotFoundException;
import erasmus.networking.common.exceptions.students.StudentAlreadyExistsException;
import erasmus.networking.common.exceptions.students.StudentNotFoundException;
import erasmus.networking.common.utils.ParseDate;
import erasmus.networking.domain.model.entity.Authority;
import erasmus.networking.domain.model.entity.Faculty;
import erasmus.networking.domain.model.entity.Student;
import erasmus.networking.domain.model.mappers.StudentMapper;
import erasmus.networking.domain.model.repository.StudentRepository;
import erasmus.networking.domain.model.repository.specifications.StudentSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final FacultyService facultyService;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Student saveStudent(StudentRequest studentRequest) {
    var faculty = facultyService.getFacultyByAbbreviation(studentRequest.getFacultyAbbreviation());
    if (faculty == null) {
      log.error(
          "Faculty with abbreviation "
              + studentRequest.getFacultyAbbreviation()
              + " does not exist");
      throw new FacultyAbbreviationNotFoundException("Faculty with abbreviation does not exist");
    }

    var student = studentRepository.findStudentByEmail(studentRequest.getEmail());
    if (student != null) {
      log.error("Student with email {} already exists", student.getEmail());
      throw new StudentAlreadyExistsException("Student with email already exists");
    }


    Student newStudent = new Student();
    newStudent.setFirstName(studentRequest.getFirstName());
    newStudent.setLastName(studentRequest.getLastName());
    newStudent.setEmail(studentRequest.getEmail());
    newStudent.setDateOfBirth(
        ParseDate.parseStringToDate(studentRequest.getDateOfBirth(), ParseDate.dateOfBirthPattern));
    newStudent.setFaculty(faculty);
    newStudent.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
    newStudent.setRole(Role.STUDENT.getRoleString());
    newStudent.setIsGraduated(false);

    Authority authority = new Authority();
    authority.setName(Role.STUDENT.getRoleString());
    authority.setStudent(newStudent);

    newStudent.setAuthorities(Set.of(authority));

    Student student1 = studentRepository.save(newStudent);

    log.info("Student {} was successfully saved", student1.getEmail());

    return student1;
  }

  public void graduateStudentFromUniversity(String email) {
    var student = studentRepository.findStudentByEmail(email);
    if (student == null) {
      log.error("Cannot graduate student: Student with email {} does not exist", email);
      throw new StudentNotFoundException("Student does not exist");
    }

    if (Boolean.TRUE.equals(student.getIsGraduated())) {
      log.error("Cannot graduate student: {} is already graduated", email);
      throw new StudentNotFoundException("Student is already graduated");
    }

    student.setIsGraduated(true);
    studentRepository.save(student);

    log.info("Student {} was successfully graduated", email);
  }

  public List<StudentDetailedResponse> getAllStudents() {
    return studentRepository.findAll().stream()
        .map(StudentMapper.mapStudentToStudentDetailedResponse)
        .toList();
  }

  public Student getStudentByEmail(String email) {
    return studentRepository.findStudentByEmail(email);
  }

  public List<StudentDetailedResponse> getStudentsEnrolledToFaculty(String facultyAbbreviation) {
    Faculty faculty = facultyService.getFacultyByAbbreviation(facultyAbbreviation);
    if (faculty == null) {
      log.error("Faculty with abbreviation " + facultyAbbreviation + " does not exist");
      throw new FacultyAbbreviationNotFoundException("Faculty with abbreviation does not exist");
    }

    List<StudentDetailedResponse> studentDetailedResponses =
        studentRepository.findStudentsByFacultyId(faculty.getId()).stream()
            .filter(student -> !student.getIsGraduated())
            .map(StudentMapper.mapStudentToStudentDetailedResponse)
            .toList();

    if (studentDetailedResponses.isEmpty()) {
      log.error("No students enrolled in faculty with abbreviation {}", facultyAbbreviation);
      throw new StudentNotFoundException(
          "No students enrolled in faculty with abbreviation " + facultyAbbreviation);
    }

    return studentDetailedResponses;
  }

  public List<StudentDetailedResponse> getStudentsGraduatedFromFaculty(String facultyAbbreviation) {
    Faculty faculty = facultyService.getFacultyByAbbreviation(facultyAbbreviation);
    if (faculty == null) {
      log.error("Faculty with abbreviation " + facultyAbbreviation + " does not exist");
      throw new FacultyAbbreviationNotFoundException("Faculty with abbreviation does not exist");
    }

    List<StudentDetailedResponse> studentDetailedResponses =
        studentRepository.findStudentsByFacultyId(faculty.getId()).stream()
            .filter(Student::getIsGraduated)
            .map(StudentMapper.mapStudentToStudentDetailedResponse)
            .toList();

    if (studentDetailedResponses.isEmpty()) {
      log.error("No students graduated from faculty with abbreviation {}", facultyAbbreviation);
      throw new StudentNotFoundException(
          "No students graduated from faculty with abbreviation " + facultyAbbreviation);
    }

    return studentDetailedResponses;
  }

  public Page<StudentDetailedResponse> searchStudents(String search, Pageable pageable) {
    StudentSpecificationsBuilder builder = new StudentSpecificationsBuilder();

    if (search != null) {
      Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+?),");
      Matcher matcher = pattern.matcher(search + ",");
      while (matcher.find()) {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
      }
    }

    Specification<Student> spec = builder.build();
    Page<Student> students = studentRepository.findAll(spec, pageable);

    return students.map(StudentMapper.mapStudentToStudentDetailedResponse);
  }

  public void deleteStudent(Long id) {
    studentRepository
        .findById(id)
        .orElseThrow(
            () -> new StudentNotFoundException("Student with id " + id + " does not exist"));
    studentRepository.deleteById(id);
  }


  public StudentDetailedResponse getStudent(Long id) {
    Student student =
        studentRepository
            .findById(id)
            .orElseThrow(
                () -> new StudentNotFoundException("Student with id " + id + " does not exist"));
    return StudentMapper.mapStudentToStudentDetailedResponse.apply(student);
  }

  public boolean canAccessStudent(Object principal, Long studentId) {
    UserDetails userDetails = (UserDetails) principal;
    Student student = studentRepository.findStudentByEmail(userDetails.getUsername());
    return student.getId().equals(studentId);
  }
}
