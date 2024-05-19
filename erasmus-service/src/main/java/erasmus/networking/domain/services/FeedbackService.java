package erasmus.networking.domain.services;

import erasmus.networking.api.responses.FeedbackResponse;
import erasmus.networking.domain.model.mappers.FeedbackMapper;
import erasmus.networking.domain.model.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(FeedbackMapper.mapToFeedbackResponseFromFeedbackEntity)
                .toList();
    }

    public FeedbackResponse getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .map(FeedbackMapper.mapToFeedbackResponseFromFeedbackEntity)
                .orElse(null);
    }

    public List<FeedbackResponse> getFeedbacksByStudentId(Long studentId) {
        return feedbackRepository.findAllByStudentId(studentId).stream()
                .map(FeedbackMapper.mapToFeedbackResponseFromFeedbackEntity)
                .toList();
    }

    public FeedbackResponse likeFeedback(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .map(feedback -> {
                    feedback.setLikes(feedback.getLikes() + 1);
                    return FeedbackMapper.mapToFeedbackResponseFromFeedbackEntity.apply(feedbackRepository.save(feedback));
                })
                .orElse(null);
    }

}
