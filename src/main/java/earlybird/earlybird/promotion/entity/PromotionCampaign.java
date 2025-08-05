package earlybird.earlybird.promotion.entity;

import earlybird.earlybird.common.BaseTimeEntity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

/** 프로모션 정책(기간, 인당 횟수 제한 등)을 관리하는 엔티티 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "promotion_campaigns")
@Entity
public class PromotionCampaign extends BaseTimeEntity {

    @Column(name = "promotion_campaigns_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 프로모션 캠페인 이름
    @Column(name = "promotion_campaigns_name", nullable = false)
    private String name;

    @Column(name = "promotion_campaigns_description")
    private String description;

    // 시간대 : 한국 시간대
    @Column(name = "promotion_campaigns_starts_time", nullable = false)
    private LocalDateTime startTime;

    // 시간대 : 한국 시간대
    @Column(name = "promotion_campaigns_ends_time", nullable = false)
    private LocalDateTime endTime;

    // 캠페인 타입
    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_campaigns_type", nullable = false)
    private PromotionCampaignType promotionCampaignType;

    // 캠페인 활성화 여부
    @Column(name = "promotion_campaigns_is_active", nullable = false)
    private Boolean active;
}
