package erasmus.networking.domain.model.mappers;

import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;
import erasmus.networking.domain.model.entity.University;
import erasmus.networking.api.requests.university.UniversityCreateRequest;
import erasmus.networking.api.responses.university.UniversityResponse;

import java.util.function.Function;

public class UniversityMapper {
  private UniversityMapper() {}

  public static final Function<UniversityCreateRequest, University> mapToUniversity =
      request -> {
        University university = new University();
        university.setName(request.name());
        return university;
      };

  public static final Function<University, UniversityResponse> mapToUniversityResponse =
      university -> new UniversityResponse(university.getId(), university.getName());
}
