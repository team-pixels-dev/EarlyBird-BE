package earlybird.earlybird.feedback.controller;

import earlybird.earlybird.feedback.controller.request.CreateFeedbackCommentRequest;
import earlybird.earlybird.feedback.controller.request.CreateFeedbackScoreRequest;
import earlybird.earlybird.feedback.controller.request.CreatePaymentFeedbackRequest;
import earlybird.earlybird.feedback.domain.comment.FeedbackCommentRepository;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackRepository;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel.WILL_PAY;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreateFeedbackControllerLoggingTest {

    @Autowired
    private CreateFeedbackController controller;

    @Autowired
    private FeedbackCommentRepository commentRepository;

    @Autowired
    private FeedbackScoreRepository scoreRepository;

    @Autowired
    private PaymentFeedbackRepository paymentRepository;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAllInBatch();
        scoreRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
    }

    @DisplayName("테스트 용 ClientId가 요청으로 들어오면 피드백을 저장하지 않는다")
    @Test
    void noLoggingWithTestClientId() {
        CreateFeedbackCommentRequest commentRequest = CreateFeedbackCommentRequest.builder()
                .clientId("test-id")
                .comment("comment")
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .build();
        controller.createFeedbackComment(null, commentRequest);
        assertThat(commentRepository.count()).isEqualTo(0);

        CreateFeedbackScoreRequest scoreRequest = CreateFeedbackScoreRequest.builder()
                .clientId("test-id")
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .score(10)
                .dayCount(3)
                .build();
        controller.createFeedbackScore(scoreRequest);
        assertThat(scoreRepository.count()).isEqualTo(0);

        CreatePaymentFeedbackRequest paymentRequest = CreatePaymentFeedbackRequest.builder()
                .clientId("test-id")
                .level(WILL_PAY)
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .build();

        controller.createPaymentFeedback(paymentRequest);
        assertThat(paymentRepository.count()).isEqualTo(0);
    }

    @DisplayName("테스트 용이 아닌 ClientId 가 요청으로 들어오면 피드백을 저장한다")
    @Test
    void loggingWithNotTestClientId() {

        assertThat(commentRepository.count()).isEqualTo(0);
        assertThat(scoreRepository.count()).isEqualTo(0);
        assertThat(paymentRepository.count()).isEqualTo(0);


        CreateFeedbackCommentRequest commentRequest = CreateFeedbackCommentRequest.builder()
                .clientId("client-id")
                .comment("comment")
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .build();
        controller.createFeedbackComment(null, commentRequest);
        assertThat(commentRepository.count()).isEqualTo(1);

        CreateFeedbackScoreRequest scoreRequest = CreateFeedbackScoreRequest.builder()
                .clientId("client-id")
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .score(10)
                .dayCount(3)
                .build();
        controller.createFeedbackScore(scoreRequest);
        assertThat(scoreRepository.count()).isEqualTo(1);

        CreatePaymentFeedbackRequest paymentRequest = CreatePaymentFeedbackRequest.builder()
                .clientId("client-id")
                .level(WILL_PAY)
                .createdAt(LocalDateTime.of(2025, 3, 25, 0, 0, 0))
                .build();

        controller.createPaymentFeedback(paymentRequest);
        assertThat(paymentRepository.count()).isEqualTo(1);
    }


}
