package erasmus.networking.domain.model.mappers;

import erasmus.networking.api.responses.FeedbackResponse;
import erasmus.networking.domain.model.entity.Feedback;

import java.util.function.Function;

public class FeedbackMapper {

    public static final Function<Feedback, FeedbackResponse> mapToFeedbackResponseFromFeedbackEntity =
            feedback -> {
                FeedbackResponse feedbackResponse = new FeedbackResponse();
                feedbackResponse.setId(feedback.getId());
                feedbackResponse.setTitle(feedback.getTitle());
                feedbackResponse.setText(feedback.getText());
                feedbackResponse.setLikes(feedback.getLikes());
                feedbackResponse.setCreatedAt(feedback.getCreatedAt().toString());
                feedbackResponse.setUpdatedAt(feedback.getUpdatedAt().toString());
                feedbackResponse.setStudent(StudentMapper.mapStudentToStudentResponse.apply(feedback.getStudent()));
                return feedbackResponse;
            };
}
