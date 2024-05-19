package erasmus.networking.api.responses;

import erasmus.networking.api.responses.student.StudentResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackResponse {

    private Long id;

    private String title;

    private String text;

    private int likes;

    private String createdAt;

    private String updatedAt;

    private StudentResponse student;
}
