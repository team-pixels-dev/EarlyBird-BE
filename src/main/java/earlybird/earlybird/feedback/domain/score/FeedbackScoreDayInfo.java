package earlybird.earlybird.feedback.domain.score;

import earlybird.earlybird.common.BaseTimeEntity;

import jakarta.persistence.*;

import lombok.*;

/** 피드백 점수(NPS 점수) 응답 Day 정보 Day 0 = 사용자 서비스 최초 사용일 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class FeedbackScoreDayInfo extends BaseTimeEntity {
    @Column(name = "feedback_score_day_info_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Integer day;

    @OneToOne
    @JoinColumn(name = "feedback_score_id")
    private FeedbackScore feedbackScore;
}
