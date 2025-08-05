package earlybird.earlybird.feedback.domain.pay;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.user.User;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class PaymentFeedback extends BaseTimeEntity {

    @Column(name = "payment_feedback_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_feedback_level", nullable = false)
    private PaymentFeedbackLevel level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String clientId;

    @Column(name = "feedback_created_time_at_client", nullable = false)
    private LocalDateTime createdTimeAtClient;
}
