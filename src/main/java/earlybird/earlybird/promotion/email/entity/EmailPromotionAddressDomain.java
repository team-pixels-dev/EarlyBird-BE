package earlybird.earlybird.promotion.email.entity;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import jakarta.persistence.*;
import lombok.*;

/**
 * 프로모션 대상 이메일의 도메인을 관리하는 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "email_promotion_address_domains")
@Entity
public class EmailPromotionAddressDomain extends BaseTimeEntity {

    @Column(name = "email_promotion_address_domains_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 프로모션 대상 이메일 주소의 도메인 이름
    @Column(name = "email_promotion_address_domains_domain_name", nullable = false)
    private String domainName;

    // 소속 프로모션 캠페인
    @JoinColumn(name = "promotion_campaigns_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PromotionCampaign promotionCampaign;
}
