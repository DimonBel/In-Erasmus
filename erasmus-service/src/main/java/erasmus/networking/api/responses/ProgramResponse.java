package erasmus.networking.api.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramResponse {

    private Long id;

    private String name;

    private String description;

    private String link;

    private String abbreviation;

    private String studyField;

    private String createdAt;

    private String tripDate;

    private String applicationDeadline;

}
