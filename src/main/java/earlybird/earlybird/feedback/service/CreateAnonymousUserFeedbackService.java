package earlybird.earlybird.feedback.service;

import earlybird.earlybird.feedback.entity.Feedback;
import earlybird.earlybird.feedback.repository.FeedbackRepository;
import earlybird.earlybird.feedback.dto.FeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateAnonymousUserFeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void create(FeedbackDTO feedbackDTO) {
        Feedback feedback = Feedback.builder()
                .content(feedbackDTO.getContent())
                .createdAt(feedbackDTO.getCreatedAt())
                .build();

        feedbackRepository.save(feedback);
    }
}
