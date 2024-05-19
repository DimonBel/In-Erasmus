package erasmus.networking.api.responses.faculty;

import java.util.List;

import erasmus.networking.api.responses.student.StudentResponse;
import erasmus.networking.api.responses.student.StudentResponse;

public record FacultyWithStudentsResponse(
    Long id, String name, String abbreviation, String studyField, List<StudentResponse> students) {}
