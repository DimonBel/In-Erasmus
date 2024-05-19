package erasmus.networking.domain.model.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import erasmus.networking.api.requests.faculty.FacultyRequest;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.faculty.FacultyWithStudentsResponse;
import erasmus.networking.common.enums.StudyField;
import erasmus.networking.domain.model.entity.Faculty;
import erasmus.networking.domain.model.entity.Student;
import erasmus.networking.api.requests.faculty.FacultyRequest;
import erasmus.networking.domain.model.entity.Faculty;
import erasmus.networking.domain.model.entity.Student;
import erasmus.networking.domain.model.entity.StudyFieldEntity;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.faculty.FacultyWithStudentsResponse;
import erasmus.networking.common.enums.StudyField;

public class FacultyMapper {

  private FacultyMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static final Function<FacultyRequest, Faculty> mapToFacultyEntityFromFacultyRequest =
      facultyRequest -> {
        Faculty faculty = new Faculty();
        faculty.setName(facultyRequest.facultyName());
        faculty.setAbbreviation(facultyRequest.abbreviation());
        return faculty;
      };

  public static final Function<Faculty, FacultyResponse> mapToFacultyResponse =
      faculty ->
          new FacultyResponse(
              faculty.getId(),
              faculty.getName(),
              faculty.getAbbreviation(),
              faculty.getStudyField().getName().name());

  public static final BiFunction<Faculty, List<Student>, FacultyWithStudentsResponse>
      mapToFacultyResponseWithStudents =
          (faculty, students) ->
              new FacultyWithStudentsResponse(
                  faculty.getId(),
                  faculty.getName(),
                  faculty.getAbbreviation(),
                  faculty.getStudyField().getName().name(),
                  students.stream().map(StudentMapper.mapStudentToStudentResponse).toList());

  public static Faculty mapToFaculty(ResultSet rs) throws SQLException {
    Faculty faculty = new Faculty();
    faculty.setId(rs.getLong("id"));
    faculty.setName(rs.getString("name"));
    faculty.setAbbreviation(rs.getString("abbreviation"));
    faculty.setCreatedAt(rs.getTimestamp("created_at").toInstant());
    faculty.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());

    String studyFieldStr = rs.getString("study_field");
    if (studyFieldStr != null) {
      StudyFieldEntity studyFieldEntity = new StudyFieldEntity();
      studyFieldEntity.setName(StudyField.valueOf(studyFieldStr));
      faculty.setStudyField(studyFieldEntity);
    }

    return faculty;
  }
}
