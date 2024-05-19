package erasmus.networking.api.responses.student;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.networking.api.responses.AchievementResponse;
import erasmus.networking.api.responses.ProgramResponse;
import erasmus.networking.api.responses.faculty.FacultyResponse;
import erasmus.networking.api.responses.university.UniversityResponse;
import erasmus.networking.domain.model.entity.Achievements;
import erasmus.networking.domain.model.entity.Program;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class StudentDetailedResponse extends StudentResponse {

    @JsonProperty("universityId")
    private Long universityId;

    @JsonProperty("facultyId")
    private Long facultyId;

    @JsonProperty("facultyResponse")
    private FacultyResponse facultyResponse;

    @JsonProperty("universityResponse")
    private UniversityResponse universityResponse;

    @JsonProperty("achievements")
    private List<AchievementResponse> achievements;

    @JsonProperty("programs")
    private List<ProgramResponse> programs;
}
