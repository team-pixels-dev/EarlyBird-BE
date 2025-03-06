package earlybird.earlybird.feedback.service.anonymous;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import earlybird.earlybird.feedback.domain.score.FeedbackScore;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfo;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfoRepository;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreRepository;
import earlybird.earlybird.feedback.service.anonymous.request.CreateAnonymousFeedbackScoreServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CreateAnonymousFeedbackScoreServiceTest {

    @Mock private FeedbackScoreRepository feedbackScoreRepository;

    @Mock private FeedbackScoreDayInfoRepository feedbackScoreDayInfoRepository;

    @InjectMocks private CreateAnonymousFeedbackScoreService service;

    @DisplayName("요청에 담긴 피드백 점수를 DB에 저장한다")
    @Test
    void createFeedbackScore() {
        CreateAnonymousFeedbackScoreServiceRequest request =
                CreateAnonymousFeedbackScoreServiceRequest.builder()
                        .createdAt(LocalDateTime.of(2025, 3, 6, 0, 0, 0))
                        .clientId("client-id")
                        .dayCount(7)
                        .score(10)
                        .build();

        service.create(request);

        verify(feedbackScoreRepository).save(any(FeedbackScore.class));
    }

    @DisplayName("요청에 담긴 사용자 Day 정보를 DB에 저장한다")
    @Test
    void createFeedbackScoreDayInfo() {
        CreateAnonymousFeedbackScoreServiceRequest request =
                CreateAnonymousFeedbackScoreServiceRequest.builder()
                        .createdAt(LocalDateTime.of(2025, 3, 6, 0, 0, 0))
                        .clientId("client-id")
                        .dayCount(7)
                        .score(10)
                        .build();

        service.create(request);

        verify(feedbackScoreDayInfoRepository).save(any(FeedbackScoreDayInfo.class));
    }
}
