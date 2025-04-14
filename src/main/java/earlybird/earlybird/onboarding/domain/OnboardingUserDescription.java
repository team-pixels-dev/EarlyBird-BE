package earlybird.earlybird.onboarding.domain;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class OnboardingUserDescription extends BaseTimeEntity {

    @Column(name = "onboarding_user_description_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "onboarding_user_description", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String clientId;

    @Column(name = "onboarding_user_description_created_time_at_client", nullable = false)
    private LocalDateTime createdTimeAtClient;

}
