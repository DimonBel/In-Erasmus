package erasmus.networking.domain.model.mappers;

import erasmus.networking.api.responses.jwt.JWT;
import erasmus.networking.api.responses.student.StudentDetailedResponse;
import erasmus.networking.api.responses.student.StudentResponse;
import erasmus.networking.api.responses.student.StudentWithJwtResponse;
import erasmus.networking.domain.model.entity.Student;

import java.util.function.BiFunction;
import java.util.function.Function;

public class StudentMapper {

    private StudentMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static final Function<Student, StudentResponse> mapStudentToStudentResponse =
            student -> {
                StudentResponse s = new StudentResponse();
                s.setId(student.getId());
                s.setFirstName(student.getFirstName());
                s.setLastName(student.getLastName());
                s.setEmail(student.getEmail());
                s.setAvatarUrl(student.getAvatarUrl());
                s.setDateOfBirth(student.getDateOfBirth());
                s.setEnrollmentDate(student.getEnrollmentDate());
                s.setIsGraduated(student.getIsGraduated());
                return s;
            };

    public static final Function<Student, StudentDetailedResponse>
            mapStudentToStudentDetailedResponse =
            student -> {
                StudentDetailedResponse sd = new StudentDetailedResponse();
                sd.setId(student.getId());
                sd.setFirstName(student.getFirstName());
                sd.setLastName(student.getLastName());
                sd.setEmail(student.getEmail());
                sd.setAvatarUrl(student.getAvatarUrl());
                sd.setDateOfBirth(student.getDateOfBirth());
                sd.setEnrollmentDate(student.getEnrollmentDate());
                sd.setIsGraduated(student.getIsGraduated());
                sd.setFacultyId(student.getFaculty().getId());
                sd.setUniversityId(student.getFaculty().getUniversity().getId());
                sd.setFacultyResponse(FacultyMapper.mapToFacultyResponse.apply(student.getFaculty()));
                sd.setPrograms(student.getPrograms().stream().map(ProgramMapper.mapToProgramResponseFromProgramEntity).toList());
                sd.setAchievements(student.getAchievements().stream().map(AchievementMapper.mapAchievementToAchievementResponse).toList());
                sd.setUniversityResponse(UniversityMapper.mapToUniversityResponse.apply(student.getFaculty().getUniversity()));
                return sd;
            };

    public static final BiFunction<Student, JWT, StudentWithJwtResponse> mapStudentToStudentWithJwtResponse = (student, jwt) -> {
        StudentWithJwtResponse swj = new StudentWithJwtResponse();
        swj.setId(student.getId());
        swj.setFirstName(student.getFirstName());
        swj.setLastName(student.getLastName());
        swj.setEmail(student.getEmail());
        swj.setAvatarUrl(student.getAvatarUrl());
        swj.setDateOfBirth(student.getDateOfBirth());
        swj.setIsGraduated(student.getIsGraduated());
        swj.setJwt(jwt);
        return swj;
    };
}
