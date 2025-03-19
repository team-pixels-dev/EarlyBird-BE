package earlybird.earlybird.feedback.service.anonymous;

import earlybird.earlybird.feedback.domain.pay.PaymentFeedback;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackRepository;
import earlybird.earlybird.feedback.service.anonymous.request.CreateAnonymousPaymentFeedbackServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateAnonymousPaymentFeedbackService {

    private final PaymentFeedbackRepository paymentFeedbackRepository;

    @Transactional
    public void create(CreateAnonymousPaymentFeedbackServiceRequest request) {
        PaymentFeedback paymentFeedback = PaymentFeedback.builder()
                .level(request.getPaymentFeedbackLevel())
                .clientId(request.getClientId())
                .createdTimeAtClient(request.getCreatedAt())
                .build();

        paymentFeedbackRepository.save(paymentFeedback);
    }
}
