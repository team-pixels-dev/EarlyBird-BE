package earlybird.earlybird.promotion.email.entity;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import jakarta.persistence.*;
import lombok.*;

/**
 * 프로모션 코드 발급 내역을 관리하는 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "email_promotion_code_issuances")
@Entity
public class EmailPromotionCodeIssuance extends BaseTimeEntity {

    @Column(name = "email_promotion_code_issuances_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 소속 프로모션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_campaigns_id", nullable = false)
    private PromotionCampaign promotionCampaign;

    // 발급한 프로모션 코드
    @Column(name = "email_promotion_code_issuances_promotion_code", nullable = false)
    private String promotionCode;

    // 발급한 프로모션 코드 사용 여부
    // 애플에서 발급한 특가 할인 URL은 방문했을 경우 TRUE로 저장
    @Column(name = "email_promotion_code_issuances_code_is_used", nullable = false)
    private Boolean promotionCodeIsUsed;
}
