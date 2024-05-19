package erasmus.networking.domain.services;

import erasmus.networking.api.requests.faculty.FacultyRequest;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.faculty.FacultyWithStudentsResponse;
import erasmus.networking.api.responses.faculty.IsBelongToFacultyResponse;
import erasmus.networking.common.enums.StudyField;
import erasmus.networking.common.exceptions.faculties.FacultyAlreadyExistsException;
import erasmus.networking.common.exceptions.faculties.FacultyNotFoundException;
import erasmus.networking.common.exceptions.faculties.InvalidFacultyRequestException;
import erasmus.networking.domain.model.entity.Faculty;
import erasmus.networking.domain.model.entity.Student;
import erasmus.networking.domain.model.entity.StudyFieldEntity;
import erasmus.networking.domain.model.entity.University;
import erasmus.networking.domain.model.mappers.FacultyMapper;
import erasmus.networking.domain.model.repository.FacultyRepository;
import erasmus.networking.domain.model.repository.StudentRepository;
import erasmus.networking.domain.model.repository.UniversityRepository;
import erasmus.networking.domain.model.repository.specifications.FacultySpecifications;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    private final UniversityRepository universityRepository;

    @Autowired
    public FacultyService(
            FacultyRepository facultyRepository,
            StudentRepository studentRepository,
            UniversityRepository universityRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<FacultyWithStudentsResponse> getAllFacultiesWithStudents(Pageable pageable) {
        Page<Faculty> faculties = facultyRepository.findAll(pageable);
        return faculties.map(
                faculty -> {
                    List<Student> students = studentRepository.findStudentsByFacultyId(faculty.getId());
                    return FacultyMapper.mapToFacultyResponseWithStudents.apply(faculty, students);
                });
    }

    public boolean isValidStudyField(String studyField) {
        try {
            StudyField.valueOf(studyField);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("Invalid studyField: {} ", studyField);
            return false;
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public FacultyResponse saveFaculty(FacultyRequest facultyRequest) {

        Faculty faculty = FacultyMapper.mapToFacultyEntityFromFacultyRequest.apply(facultyRequest);

        if (!isValidStudyField(facultyRequest.studyField())) {
            log.error("Invalid study field provided: {}", facultyRequest.studyField());
            throw new InvalidFacultyRequestException(
                    "Invalid study field provided: " + facultyRequest.studyField());
        }

        Faculty existingFaculty =
                facultyRepository.findFacultyByAbbreviation(facultyRequest.abbreviation());
        if (existingFaculty != null) {
            log.error("Faculty with abbreviation {} already exists", facultyRequest.abbreviation());
            throw new FacultyAlreadyExistsException(
                    "Faculty with abbreviation " + facultyRequest.abbreviation() + " already exists");
        }

        StudyFieldEntity studyFieldEntity = getStudyFieldEntity(facultyRequest);

        University university =
                universityRepository.findById(facultyRequest.universityId()).orElse(null);
        if (university == null) {
            log.error("University not found with ID: {}", facultyRequest.universityId());
            throw new IllegalArgumentException(
                    "University not found with ID: " + facultyRequest.universityId());
        }

        faculty.setStudyField(studyFieldEntity);
        faculty.setUniversity(university);
        Faculty facultyCreated = facultyRepository.save(faculty);

        log.info("Faculty created successfully.");

        return FacultyMapper.mapToFacultyResponse.apply(facultyCreated);
    }

    private static StudyField getStudyField(String studyFieldStr) {
        StudyField studyFieldEnum;
        try {
            studyFieldEnum = StudyField.valueOf(studyFieldStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid study field provided: {}", studyFieldStr);
            return null;
        }
        return studyFieldEnum;
    }

    public List<FacultyResponse> getAllFacultiesByField(String studyField) {
        StudyField studyFieldEnum = getStudyField(studyField);
        if (studyFieldEnum == null) {
            return List.of();
        }

        if (isValidStudyField(studyField)) {
            List<Faculty> faculties =
                    facultyRepository
                            .findAllFacultiesByStudyFieldName(studyFieldEnum)
                            .orElseThrow(
                                    () ->
                                            new InvalidFacultyRequestException(
                                                    "No faculties found for study field: " + studyField));
            return faculties.stream().map(FacultyMapper.mapToFacultyResponse).toList();
        }

        return List.of();
    }

    public Faculty getFacultyByAbbreviation(String abbreviation) {
        return facultyRepository.findFacultyByAbbreviation(abbreviation);
    }

    public FacultyWithStudentsResponse getStudentBelongingToFaculty(String studentEmail) {
        Faculty faculty =
                facultyRepository
                        .findFacultyBelongingToStudent(studentEmail)
                        .orElseThrow(
                                () ->
                                        new FacultyNotFoundException(
                                                "No faculty found for student with email: " + studentEmail));

        return FacultyMapper.mapToFacultyResponseWithStudents.apply(faculty, faculty.getStudents());
    }

    public IsBelongToFacultyResponse isBelongToFacultyResponse(
            String studentEmail, String facultyAbbreviation) {
        FacultyWithStudentsResponse faculty = getStudentBelongingToFaculty(studentEmail);
        if (facultyAbbreviation.equals(faculty.abbreviation())) {
            log.info("Student with email {} belongs to this faculty", studentEmail);
            return new IsBelongToFacultyResponse(true);
        } else {
            log.info("Student with email {} does not belong to this faculty", studentEmail);
            return new IsBelongToFacultyResponse(false);
        }
    }

    public boolean isAuthorized(String studentEmail, String facultyAbbreviation) {
        FacultyWithStudentsResponse faculty = getStudentBelongingToFaculty(studentEmail);
        return facultyAbbreviation.equals(faculty.abbreviation());
    }


    public Page<FacultyResponse> searchFaculties(
            String name,
            String abbreviation,
            Long universityId,
            Instant createdAfter,
            Instant createdBefore,
            Pageable pageable) {
        Specification<Faculty> spec =
                FacultySpecifications.combine(
                        FacultySpecifications.nameContains(name),
                        FacultySpecifications.abbreviationContains(abbreviation),
                        FacultySpecifications.universityIdEquals(universityId),
                        FacultySpecifications.createdAfter(createdAfter),
                        FacultySpecifications.createdBefore(createdBefore));
        return facultyRepository.findAll(spec, pageable).map(FacultyMapper.mapToFacultyResponse);
    }

    @Transactional

    public void updateFaculty(Long id, FacultyRequest facultyRequest) {
        Faculty faculty =
                facultyRepository
                        .findById(id)
                        .orElseThrow(() -> new FacultyNotFoundException("Faculty not found with ID: " + id));

        if (!isValidStudyField(facultyRequest.studyField())) {
            log.error("Invalid study field provided: {}", facultyRequest.studyField());
            throw new InvalidFacultyRequestException(
                    "Invalid study field provided: " + facultyRequest.studyField());
        }

        StudyFieldEntity studyFieldEntity = getStudyFieldEntity(facultyRequest);

        University university =
                universityRepository.findById(facultyRequest.universityId()).orElse(null);
        if (university == null) {
            log.error("University not found with ID: {} ", facultyRequest.universityId());
            throw new IllegalArgumentException(
                    "University not found with ID: " + facultyRequest.universityId());
        }

        faculty.setName(facultyRequest.facultyName());
        faculty.setAbbreviation(facultyRequest.abbreviation());
        faculty.setStudyField(studyFieldEntity);
        faculty.setUniversity(university);

        facultyRepository.save(faculty);

        log.info("Faculty updated successfully.");
    }

    private StudyFieldEntity getStudyFieldEntity(FacultyRequest facultyRequest) {
        StudyField studyFieldEnum = getStudyField(facultyRequest.studyField());
        if (studyFieldEnum == null) {
            log.error("Invalid study field provided: {} ", facultyRequest.studyField());
            throw new InvalidFacultyRequestException(
                    "Invalid study field provided: " + facultyRequest.studyField());
        }

        StudyFieldEntity studyFieldEntity = facultyRepository.findStudyFieldByName(studyFieldEnum);
        if (studyFieldEntity == null) {
            log.error("Study field not found: {} ", facultyRequest.studyField());
            throw new InvalidFacultyRequestException(
                    "Study field not found: " + facultyRequest.studyField());
        }
        return studyFieldEntity;
    }

    public void deleteFaculty(Long id) {
        Faculty faculty =
                facultyRepository
                        .findById(id)
                        .orElseThrow(() -> new FacultyNotFoundException("Faculty not found with ID: " + id));

        facultyRepository.delete(faculty);

        log.info("Faculty deleted successfully.");
    }


    public FacultyResponse getFacultyById(Long id) {
        Faculty faculty =
                facultyRepository
                        .findById(id)
                        .orElseThrow(() -> new FacultyNotFoundException("Faculty not found with ID: " + id));

        return FacultyMapper.mapToFacultyResponse.apply(faculty);
    }
}
