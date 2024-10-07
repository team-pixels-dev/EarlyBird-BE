package earlybird.earlybird.feedback.service;

import earlybird.earlybird.feedback.entity.Feedback;
import earlybird.earlybird.feedback.repository.FeedbackRepository;
import earlybird.earlybird.feedback.dto.FeedbackDTO;
import earlybird.earlybird.user.entity.User;
import earlybird.earlybird.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateAuthUserFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public void create(FeedbackDTO feedbackDTO) {

        Long userId = feedbackDTO.getUserAccountInfoDTO().getId();

        User user = userRepository.findById(userId).orElseThrow();

        Feedback feedback = Feedback.builder()
                .content(feedbackDTO.getContent())
                .user(user)
                .createdAt(feedbackDTO.getCreatedAt())
                .build();

        feedbackRepository.save(feedback);
    }
}
