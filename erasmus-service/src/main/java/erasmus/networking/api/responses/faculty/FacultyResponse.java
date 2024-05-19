package erasmus.networking.api.responses.faculty;


public record FacultyResponse(Long id, String name, String abbreviation, String studyField) {
  @Override
  public String toString() {
    return "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", abbreviation='"
        + abbreviation
        + '\''
        + ", studyField="
        + studyField;
  }
}
