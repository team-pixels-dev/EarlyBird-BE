package earlybird.earlybird.feedback.service.anonymous;

import static earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel.WILL_PAY;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import earlybird.earlybird.feedback.domain.pay.PaymentFeedback;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackRepository;
import earlybird.earlybird.feedback.service.anonymous.request.CreateAnonymousPaymentFeedbackServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CreateAnonymousPaymentFeedbackServiceTest {

    @InjectMocks private CreateAnonymousPaymentFeedbackService service;

    @Mock private PaymentFeedbackRepository repository;

    @DisplayName("요청에 담긴 결제 의향 피드백 정보를 DB에 저장한다")
    @Test
    void createPaymentFeedback() {
        // given
        CreateAnonymousPaymentFeedbackServiceRequest request =
                CreateAnonymousPaymentFeedbackServiceRequest.builder()
                        .paymentFeedbackLevel(WILL_PAY)
                        .createdAt(LocalDateTime.of(2025, 3, 19, 0, 0, 0))
                        .clientId("client_id")
                        .build();

        ArgumentCaptor<PaymentFeedback> captor = ArgumentCaptor.forClass(PaymentFeedback.class);

        // when
        service.create(request);

        // then
        verify(repository).save(captor.capture());
        PaymentFeedback saved = captor.getValue();

        assertThat(saved.getLevel()).isEqualTo(WILL_PAY);
        assertThat(saved.getCreatedTimeAtClient())
                .isEqualTo(LocalDateTime.of(2025, 3, 19, 0, 0, 0));
        assertThat(saved.getClientId()).isEqualTo("client_id");
    }
}
