package earlybird.earlybird.feedback.service.anonymous;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import earlybird.earlybird.feedback.domain.score.FeedbackScore;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfo;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfoRepository;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreRepository;
import earlybird.earlybird.feedback.service.anonymous.request.CreateAnonymousFeedbackScoreServiceRequest;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest
class CreateAnonymousFeedbackScoreServiceTest {

    @Autowired
    private CreateAnonymousFeedbackScoreService service;

    @Autowired
    private FeedbackScoreRepository feedbackScoreRepository;

    @Autowired
    private FeedbackScoreDayInfoRepository feedbackScoreDayInfoRepository;

    @Autowired
    private EntityManager entityManager;

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
        entityManager.flush();
        entityManager.clear();

        List<FeedbackScore> all = feedbackScoreRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
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

        assertThat(feedbackScoreDayInfoRepository.findAll().size()).isEqualTo(0);
        service.create(request);

        entityManager.flush();
        entityManager.clear();

        assertThat(feedbackScoreDayInfoRepository.findAll().size()).isEqualTo(1);
    }
}
