package earlybird.earlybird.onboarding.domain;

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
public class OnboardingStressAvoidReason extends BaseTimeEntity {

    @Column(name = "onboarding_stress_avoid_reason_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "onboarding_stress_avoid_reason", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String clientId;

    @Column(name = "onboarding_stress_avoid_reason_created_time_at_client", nullable = false)
    private LocalDateTime createdTimeAtClient;
}
