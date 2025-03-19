package earlybird.earlybird.feedback.service.anonymous.request;

import earlybird.earlybird.feedback.controller.request.CreatePaymentFeedbackRequest;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateAnonymousPaymentFeedbackServiceRequest {

    private PaymentFeedbackLevel paymentFeedbackLevel;
    private LocalDateTime createdAt;
    private String clientId;

    @Builder
    private CreateAnonymousPaymentFeedbackServiceRequest(
            PaymentFeedbackLevel paymentFeedbackLevel, LocalDateTime createdAt, String clientId) {
        this.paymentFeedbackLevel = paymentFeedbackLevel;
        this.createdAt = createdAt;
        this.clientId = clientId;
    }

    public static CreateAnonymousPaymentFeedbackServiceRequest of(
            CreatePaymentFeedbackRequest request) {
        return CreateAnonymousPaymentFeedbackServiceRequest.builder()
                .paymentFeedbackLevel(request.getLevel())
                .clientId(request.getClientId())
                .createdAt(request.getCreatedAt())
                .build();
    }

}
